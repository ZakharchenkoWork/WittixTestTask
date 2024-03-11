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

Every screen have it's own Routing file, where we are setting up ViewModel and pass State further to the Composables</br>
Also here we are storing Routing information, receiving data from other screens etc.</br>

Composable files are divided by logic blocks. Some of them are reusable, so they are designed that way. Other are belong only for specific screens so they are depending on screen specific State. </br>
I've decided to keep it that way so the code would be a little bit more cleaner and readable.</br>

Screen State consists mainly from MutableState, so that composable can use their data by remember {state}.</br>
This way we can update specific Composables on the screen without triggering full screen reload.</br>
If we still need to reload whole screen we still always can update MutableState with state.copy()</br>
I've tried to make UI as pixel perfect as possible, especially in fonts and sizes.</br>

Also there are some fancy animations added in default Android way and my beloved Lottie, and custom ripple efects for example in Floating action button.</br>
By the check out my solution for bottom nav bar, cut out. It was quite chalange to create it. </br>
I had to use Bezie cubic curve with subtracting of the path from the bounding rectangle. Pretty cool right?</br>

Also Floating action button in design has gradient, so i neaded to create my own version of FAB instead of standart Android one.</br>

One last thing. I decided to add a little bit of domain layer, so the ViewModel looks a little bit more production ready.</br>




