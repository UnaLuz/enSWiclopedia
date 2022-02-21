package com.unaluzdev.enswiclopedia.data

class CharacterProvider {
    companion object {

        fun characters(): List<CharacterModel> {
            return characterList
        }

        private val characterList: List<CharacterModel> = listOf(
            CharacterModel(
                birth_year = "19BBY",
                created = "2014-12-09T13:50:51.644000Z",
                edited = "2014-12-20T21:17:56.891000Z",
                eye_color = "blue",
                films = listOf(
                    "https://swapi.py4e.com/api/films/1/",
                    "https://swapi.py4e.com/api/films/2/",
                    "https://swapi.py4e.com/api/films/3/",
                    "https://swapi.py4e.com/api/films/6/",
                    "https://swapi.py4e.com/api/films/7/"
                ),
                gender = "male",
                hair_color = "blond",
                height = "172",
                homeworld = "https://swapi.py4e.com/api/planets/1/",
                mass = "77",
                name = "Luke Skywalker",
                skin_color = "fair",
                species = listOf(
                    "https://swapi.py4e.com/api/species/1/"
                ),
                starships = listOf(
                    "https://swapi.py4e.com/api/starships/12/",
                    "https://swapi.py4e.com/api/starships/22/"
                ),
                url = "https://swapi.py4e.com/api/people/1/",
                vehicles = listOf(
                    "https://swapi.py4e.com/api/vehicles/14/",
                    "https://swapi.py4e.com/api/vehicles/30/"
                )
            ),
            CharacterModel(
                birth_year = "112BBY",
                created = "2014-12-10T15:10:51.357000Z",
                edited = "2014-12-20T21:17:50.309000Z",
                eye_color = "yellow",
                films = listOf(
                    "https://swapi.py4e.com/api/films/1/",
                    "https://swapi.py4e.com/api/films/2/",
                    "https://swapi.py4e.com/api/films/3/",
                    "https://swapi.py4e.com/api/films/4/",
                    "https://swapi.py4e.com/api/films/5/",
                    "https://swapi.py4e.com/api/films/6/"
                ),
                gender = "n/a",
                hair_color = "n/a",
                height = "167",
                homeworld = "https://swapi.py4e.com/api/planets/1/",
                mass = "75",
                name = "C-3PO",
                skin_color = "gold",
                species = listOf(
                    "https://swapi.py4e.com/api/species/2/"
                ),
                starships = listOf(),
                url = "https://swapi.py4e.com/api/people/2/",
                vehicles = listOf()

            )
        )
    }
}