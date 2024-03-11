package com.faigenbloom.testtask.ui.send.compose

const val MAIN_SCREEN_SCROLL = "MAIN_SCREEN_SCROLL"
const val TRANSFER_BUTTON = "Transfer button"
const val PURPOSE_TYPE = "Purpose dropdown"
const val CONTAINER = "container for "

fun containerDescriptionFor(childContentDescripton: String) = "$CONTAINER $childContentDescripton"