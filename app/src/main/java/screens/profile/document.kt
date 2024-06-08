package screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Document(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Документация приложения",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = """
                Здесь вы можете найти документацию по использованию приложения. 
                
                1. **Добавление товаров в корзину**: Нажмите на товар, чтобы просмотреть его подробную информацию и добавить в корзину.
                2. **Просмотр корзины**: Перейдите в раздел корзины, чтобы увидеть все добавленные товары, их стоимость и скидки.
                3. **Оформление заказа**: В корзине нажмите кнопку "оформить заказ", чтобы завершить покупку.
                4. **Профиль пользователя**: В разделе профиля вы можете просмотреть и изменить личную информацию, а также ознакомиться с документами приложения.
                
                Для получения дополнительной информации или помощи обратитесь в службу поддержки.
                """.trimIndent(),
            style = MaterialTheme.typography.body1
        )
    }
}