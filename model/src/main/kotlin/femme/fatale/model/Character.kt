package femme.fatale.model

import femme.fatale.model.util.NameGenerator
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    var species: Species = Species.entries.random(),
    var race: Race = Race.entries.random(),
    var sex: Sex = Sex.entries.random(),
    var name: String = NameGenerator.generate(sex),
)
