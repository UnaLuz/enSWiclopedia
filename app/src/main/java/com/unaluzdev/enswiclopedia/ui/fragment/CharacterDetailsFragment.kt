package com.unaluzdev.enswiclopedia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unaluzdev.enswiclopedia.R
import com.unaluzdev.enswiclopedia.databinding.FragmentCharacterDetailsBinding
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.ui.CharacterDetailsViewModel
import com.unaluzdev.enswiclopedia.ui.CharacterViewModel
import com.unaluzdev.enswiclopedia.ui.adapter.SpeciesAdapter
import com.unaluzdev.enswiclopedia.ui.helper.renderImageWith

class CharacterDetailsFragment : Fragment() {
    // Args
    private var characterID: Int? = null
    private var name: String? = null

    // ViewBinding
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel
    private val characterViewModel: CharacterViewModel by activityViewModels()
    private val characterDetailsViewModel: CharacterDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterID = it.getInt(CHARACTER_ID)
            name = it.getString(NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSpeciesRecyclerView()

        // Observers
        characterDetailsViewModel.homeworldState.observe(viewLifecycleOwner) { state ->
            if (state.loading) {
                binding.homeworldInfo.horizontalCardTextView.text = getString(R.string.loading)
            } else if (state.planet != null) {
                binding.homeworldInfo.horizontalCardTextView.text = state.planet.name
                renderImageWith(
                    requireContext(),
                    binding.homeworldInfo.horizontalCardImageView,
                    state.planet.imgUrl
                ) { circleCrop() }
            }
            showError(state.error)
        }

        characterDetailsViewModel.speciesState.observe(viewLifecycleOwner) { state ->
            if (state.loading) {
                showSpeciesPlaceholder(R.string.loading)
            } else {
                if (state.species.isNullOrEmpty()) {
                    showSpeciesPlaceholder(R.string.unknown, showImage = false)
                } else {
                    hideSpeciesPlaceHolder()
                    (binding.speciesInfo.adapter as SpeciesAdapter?)?.updateList(state.species!!)
                }
            }
            showError(state.error)
        }

        getCharacterById(characterID)?.apply {
            renderStaticInfo()
            loadDynamicInfo()
        } ?: run {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_character_found),
                Toast.LENGTH_SHORT
            )
        }
    }

    private fun showSpeciesPlaceholder(@StringRes res: Int, showImage: Boolean = true) {
        with(binding.speciesPlaceholder) {
            root.visibility = View.VISIBLE
            horizontalCardTextView.text = getString(res)
            horizontalCardImageView.visibility = if (showImage) View.VISIBLE else View.GONE
        }
    }

    private fun hideSpeciesPlaceHolder() {
        with(binding.speciesPlaceholder) {
            root.visibility = View.GONE
            horizontalCardTextView.text = null
            horizontalCardImageView.visibility = View.GONE
        }
    }

    private fun setupSpeciesRecyclerView() {
        binding.speciesInfo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.speciesInfo.adapter = SpeciesAdapter(emptyList())
    }

    private fun showError(error: String?) {
        error?.let { message ->
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun SWCharacter.renderStaticInfo() {
        let { character ->
            with(binding) {
                birthDateInfo.text = character.birth_year
                hairColorInfo.text = character.hair_color
                genderInfo.text = character.gender
                eyeColorInfo.text = character.eye_color
                heightInfo.text = character.height
                massInfo.text = character.mass
                filmsInfo.text = character.films.joinToString(", ")
            }
        }
    }

    private fun SWCharacter.loadDynamicInfo() {
        renderImageWith(requireContext(), binding.characterImage, this.imgUrl)
        characterDetailsViewModel.onLoadHomeworld(this.homeworld)
        characterDetailsViewModel.onLoadSpecies(this.species)
    }

    private fun getCharacterById(characterID: Int?): SWCharacter? =
        characterViewModel.getCharacter { it.id == characterID }

    companion object {
        private const val CHARACTER_ID = "id"
        private const val NAME = "name"

        const val TAG = "CharacterDetailsFragment"

        @JvmStatic
        fun newInstance(id: Int, name: String) =
            CharacterDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHARACTER_ID, id)
                    putString(NAME, name)
                }
            }
    }
}