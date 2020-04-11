<!---
T.i.M. HTML Document
Copyright and Creator: Lunar-Tales©
Date: December, 2016
-->
<meta charset="UTF-8" />

<h1 >Trains In Motion</h1>
<p><i>Trains In Motion</i> is a Minecraft Mod built using Minecraft Forge.</p>
<p><i>Trains In Motion</i> alters the looks of, and adds new functionality, to minecarts. Allowing what was once simple minecarts, to become trains and rolling-stock, able to transport almost anything at speeds higher than a normal minecart.</p>

<table>
<tr>
<td>
<h2>Join the Discussion:</h2>
<ul>
<li> Public Discord Chat: <a href="https://discord.gg/SgpnCnK">https://discord.gg/SgpnCnK</a></li>
<li> Plans and Goals via Trello: <a href="https://trello.com/b/cqaw1T3R/trains-in-motion">https://trello.com/b/cqaw1T3R/trains-in-motion</a></li>
</ul>
</td>
<td>
<h2> Current Status:</h2>
<ul>
<li> Builds - <b id="Build">stable-ish, and VERY outdated</b></li>
<li> Revision - <b id="Revision"><a href="https://github.com/EternalBlueFlame/Trains-In-Motion/releases/">Alpha 0.2.5, Grab it here</a></b></li>
</ul>
</td>
</tr>
</table>

<h1>Contributing</h1>

<table>
<tr>
<td>
<h2>Art:</h2>
<ul>
<li> Models must match the Mod's Art-style.</li>
<li> Format is limited to <b>.java</b> models only; you can use tools such as FMT, BDCraft Cubik, SMP Toolbox, Technie or even writing it manually. FMT is free and the preferred editor due to varying shape support.</li>
<li> Model scale is <b>0.75 meters, or, 12 microblocks, wide for the wheel base</b>, In other words they must fit on minecart rails. It is reccomended the body should be 1.25+ meters, or, 20+ microblocks, wide.</li>
<li> Technie models are required to be 512x512 resolution for textures. Models from other editors have a max resolution of 4096x4096 as defined in the editor.</li>
<li> Textures must be in <b>.png</b> format.</li>
<li> Audio must be in <b>.ogg</b> format and no more than 192kb/s.</li>
</ul>
</td>
<td>
<h2>Code:</h2>
<ul>
<li> Code will be reviewed for optimizations and structure, if the issues are too large they will need to be fixed.</li>
<li> Code requires Intellij 2018, available <a href ="https://www.jetbrains.com/idea/download/other.html">here</a>, and will not work with 2019 or newer, NOTE: 2018 can be installed alongside 2019 without any conflictions. NOTE 2:Eclipse should work, but is unsupported because I don't know how to use it. </li>
<li> Code setup also requires the setup from the "Compiling a .jar pre-release" section.</li>
<li> Regarding new features/behavior changes, please submit a new Suggestion to the issue tracker, or ask me on discord, before you write any code.</li>
<li> Workspace setup may use the idea option in the setup.bat, or by entering the following command manually, without quotes. NOTE: Powershell is not supported, use CMD. And do not run as admin/sudo.</li>
<li> windows: "gradlew setupDecompWorkspace --refresh-dependencies idea" Linux/mac: "./gradlewLinux setupDecompWorkspace --refresh-dependencies idea"
</ul>
<h2> Compiling a .jar pre-release:</h2>
<ul>
<li> Compiling requires java 1.8, available for windows <a href ="https://filehippo.com/download_java_development_kit_64/86378/">here</a>. For Linux and Mac use OpenJDK. An Oracle link was not provided due to Oracle restricting various versions to buisness accounts only.</li>
<li> compiling also requires this java version as your system default, in windows this is done by going to Enviornment Variables in Advanced System Settings, and adding or changing "JAVA_HOME" to the install path of jdk 8.</li>
<li> windows: "gradlew setupDecompWorkspace --refresh-dependencies build" Linux/mac: "./gradlewLinux setupDecompWorkspace --refresh-dependencies build"</li>
<li> the resulting .jar file will appear in the folder /build/libs</li>
</ul>
</td>
</tr>
</table>

<h1>License</h1>

<table>
<td>
<h2>Simplified:</h2>
<h3>Turbo Model Thingy</h3>
<ul>
<li> TMT has it's own license separate from this project. See <a href="https://github.com/EternalBlueFlame/Trains-In-Motion/blob/master/src/main/java/fexcraft/tmt/slim/README.md">src/main/java/fexcraft/tmt/slim/README.md</a></li>
</ul>

<h3>Modpacks and Servers:</h3>
<ul>
<li> You are allowed to use the compiled .jar in your public and private modpacks without permission.</li>
<li> The mod is to bo considered as "experimental" and whether directly, indirectly, or otherwise, we are not responsible for any damages to your server, be it in data, userbase, or any other form.</li>
</ul>

<h3>Videos and Media:</h3>
<ul>
<li> You may freely make, share, and earn revenue from videos, pictures, and reviews containing Trains in Motion.</li>
</ul>
<h3>Distribution:</h3>
<ul>
<li> Unless you are the creator of the contributed content in question, you may not redistribute the Trains in Motion source or assets, including but not limited to models, pictures, and sounds.</li>
<li> Outside of a modpack you are strictly prohibited from distributing the compiled mod in any format or medium, the only exception being the official CurseForge page of Trains in Motion (Unreleased) and other mediums approved by EternalBlueFlame</li>
<li> Trains in Motion code is planned/intended to be heavily documented in hopes it can teach some tricks to modding, but because it's nearly impossible to learn from copy-pasting code it's heavily advised against doing so without studying how and why it works.</li>
</ul>
</td>
<td>
<h2>Full License:</h2>
<ul>
<li> TMT has it's own license separate from this project. See <a href="https://github.com/EternalBlueFlame/Trains-In-Motion/blob/master/src/main/java/ebf/tim/models/tmt/ModelRendererTurbo.java#L21">src/main/java/ebf/tim/models/tmt/ModelRendererTurbo.java</a></li>
<li>See the full TiM License.md here: <a href="https://github.com/EternalBlueFlame/Trains-In-Motion/blob/master/TiM-License.md">https://github.com/EternalBlueFlame/Trains-In-Motion/blob/master/TiM-License.md</a> </li>
</ul>
</td>
</table>
