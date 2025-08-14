package com.card.unoshare.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.card.unoshare.language.I18nManager
import i18n.I18nKeys

/**
 * @author xinggen.guo
 * @date 2025/8/14 13:29
 * @description
 */
@Composable
fun NameInputDialog(
    onNameEntered: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = { /* block dismiss */ },
        title = { Text(I18nManager.get(I18nKeys.enterName)) },
        text = {
            Column {
                Text(I18nManager.get(I18nKeys.welcome))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(I18nManager.get(I18nKeys.playerName)) }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val finalName = if (name.isNotBlank()) name.trim() else generateRandomName()
                    onNameEntered(finalName)
                }
            ) {
                Text(I18nManager.get(I18nKeys.start))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    val randomName = generateRandomName()
                    onNameEntered(randomName)
                }
            ) {
                Text(I18nManager.get(I18nKeys.randomName))
            }
        }
    )
}

fun generateRandomName(): String {
    val adjectives = listOf("Swift", "Brave", "Lucky", "Fierce", "Cool", "Mighty")
    val animals = listOf("Tiger", "Panda", "Eagle", "Shark", "Fox", "Lion")
    val number = (100..999).random()

    val adj = adjectives.random()
    val animal = animals.random()

    return "$adj$animal$number"
}