package com.unaluzdev.enswiclopedia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.unaluzdev.enswiclopedia.databinding.FragmentCharacterDetailsBinding
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.ui.CharacterViewModel

private const val CHARACTER_ID = "id"
private const val NAME = "name"

class CharacterDetailsFragment : Fragment() {
    // Args
    private var characterID: Int? = null
    private var name: String? = null

    // ViewBinding
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel
    private val characterViewModel: CharacterViewModel by activityViewModels()

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
        getCharacterById(characterID)?.render() ?: run {
            Toast.makeText(requireContext(), "No character found", Toast.LENGTH_SHORT)
        }
    }

    private fun SWCharacter.render() {
        let { character ->
            with(binding) {
                renderImageWith(this.characterImage, character.imgUrl)
                birthDateInfo.text = character.birth_year
                homeworldInfo.text = character.homeworld
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

    private fun renderImageWith(imageView: ImageView, url: String) {
        Glide.with(requireContext())
            .load(url)
            .into(imageView)
    }

    private fun getCharacterById(characterID: Int?): SWCharacter? =
        characterViewModel.getCharacter { it.id == characterID }

    companion object {
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