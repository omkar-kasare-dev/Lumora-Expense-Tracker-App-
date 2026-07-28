package com.finance.lumora.presentation.transaction.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.finance.lumora.domain.model.SubCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoryDropDown(

    subCategories: List<SubCategory>,

    selectedSubCategory: SubCategory?,
    enabled: Boolean = true,

    onSubCategorySelected: (SubCategory) -> Unit,

    onAddSubCategoryClick: () -> Unit,

    modifier: Modifier = Modifier

) {

    var expanded by remember {

        mutableStateOf(false)

    }

    ExposedDropdownMenuBox(

        expanded = expanded,

        onExpandedChange = {

            expanded = !expanded

        },

        modifier = modifier.fillMaxWidth()

    ) {

        OutlinedTextField(

            value = selectedSubCategory?.name ?: "",

            onValueChange = {},

            readOnly = true,

            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),

            label = {

                Text("SubCategory")

            },

            trailingIcon = {

                ExposedDropdownMenuDefaults.TrailingIcon(expanded)

            }

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            if (subCategories.isEmpty()) {

                DropdownMenuItem(
                    text = {
                        Text("No SubCategories Available")
                    },
                    enabled = false,
                    onClick = {}
                )

            } else {

                subCategories.forEach { subCategory ->

                    DropdownMenuItem(
                        text = {
                            Text(subCategory.name)
                        },
                        onClick = {
                            onSubCategorySelected(subCategory)
                            expanded = false
                        }
                    )

                }

            }

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Text("➕ Add New SubCategory")
                },
                onClick = {
                    expanded = false
                    onAddSubCategoryClick()
                }
            )

        }

    }

}