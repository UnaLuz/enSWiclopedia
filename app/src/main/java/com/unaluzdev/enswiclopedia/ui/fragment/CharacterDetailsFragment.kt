package com.unaluzdev.enswiclopedia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.unaluzdev.enswiclopedia.R
import com.unaluzdev.enswiclopedia.databinding.FragmentCharacterDetailsBinding
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.ui.CharacterDetailsViewModel
import com.unaluzdev.enswiclopedia.ui.CharacterViewModel

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

        // Observers
        characterDetailsViewModel.homeworldState.observe(viewLifecycleOwner) { state ->
            if (state.loading) {
                binding.homeworldInfo.text = getString(R.string.loading)
            } else if (state.planet != null) {
                binding.homeworldInfo.text = state.planet.name
            }
            state.error?.let { message ->
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        getCharacterById(characterID)?.apply {
            renderStaticInfo()
            loadDinamicInfo()
        } ?: run {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_character_found),
                Toast.LENGTH_SHORT
            )
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
                speciesInfo.text = character.species.joinToString(", ")
                filmsInfo.text = character.films.joinToString(", ")
            }
        }
    }

    private fun SWCharacter.loadDinamicInfo() {
        renderImageWith(binding.characterImage, this.imgUrl)
        characterDetailsViewModel.onLoadHomeworld(this.homeworld)
    }

    private fun renderImageWith(imageView: ImageView, url: String) {
        Glide.with(requireContext())
            .load(url)
            .into(imageView)
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