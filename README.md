[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=true)](http://www.apache.org/licenses/LICENSE-2.0)
<h1 align="center">Hi there, I'm <a href="www.linkedin.com/in/konstantyn-zakharchenko" target="_blank">Konstantyn</a> 
<img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="32"/></h1>
<h3 align="center">Welcome to Test task for Wittix</h3>

# Here are some notes.
### Tasks are logged in <a href="https://trello.com/b/meNfxVgV/testtask">Trello</a>

### Design used is in <a href="https://www.figma.com/file/SjsOeb5VUdgcxTREVOdrUo/Mivhan?type=design&node-id=0-121&mode=design&t=Bgv3QOEqq1ClANEh-0">Figma</a>

#### Few words about Architecture

I decided to go with basic  Clean Architecture + MVVM + Jetpack Compose + Android Architecture Components

MainActivity is used just for Navigation between components and showing Bottom bar with Floating Action Button.</br>

Every screen have its own Routing file, where we are setting up ViewModel and pass State further to the Composables</br>
Also here we are storing Routing information, receiving data from other screens etc.</br>

Composable files are divided by logic blocks. Some of them are reusable, so they are designed that way. Others belong only for specific screens, so they are depending on screen specific State. </br>
I've decided to keep it that way so the code would be a bit cleaner and readable.</br>

Screen State consists mainly from mutableStateOf(Smth), so that composable can use their data by remember {state.smth}.</br>
This way we can update specific Composables on the screen without triggering full screen reload.</br>
If we still need to reload whole screen, we still can update whole screen with _stateFlow.update{ it.copy()}</br>

I've tried to make UI as pixel perfect as possible, especially in fonts and sizes.</br>
Also, there are some fancy animations added in default Android way and my beloved Lottie, and custom ripple effects  for example in Floating action button.</br>
By the way, check out my solution for bottom nav bar, cutout. It was quite a challange to create it. </br>
I had to use Bezie cubic curve with subtracting of the path from the bounding rectangle to create this shape, so it can be resized for different screens properly.</br>
<div>
  <img src="images/bezie.png" width="20%"/>
</div>
Pretty cool right?</br>

Also, Floating action button in design has gradient, so I needed to create my own version of FAB instead of standard Android one.</br>

One last thing. I decided to add a bit of domain layer, so the ViewModel looks a bit more production ready.</br>

## In case you are still not convinced.
### Just try out some instrumented tests in Android Test folder.

