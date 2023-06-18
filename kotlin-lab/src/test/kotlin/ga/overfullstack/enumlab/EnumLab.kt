package ga.overfullstack.enumlab

import org.junit.jupiter.api.Test

class EnumLab {
  @Test
  fun `print enum`() {
    printAllValues<RGB>()
  }

  @Test
  fun `Enum to List`() {
    val rgbList: List<RGB> = listOf(RGB.BLUE, RGB.GREEN, RGB.RED)
  }
}
