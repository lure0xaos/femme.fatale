package femme.fatale

import femme.fatale.app.FemmeFataleModule
import femme.fatale.kfx.launch
import femme.fatale.kfx.util.getUrl
import org.koin.ksp.generated.module

private const val LOADING = "loading.png"

fun main(args: Array<String>) {
    launch(FemmeFataleModule().module, args, FemmeFataleModule::class.getUrl(LOADING))
}
