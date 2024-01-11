# macos

## Runtime Environment
First of all, you need to prepare [XEMU](https://github.com/lgblgblgb/xemu/) for our system. We build the *next* branch.

### Build XEMU
You need the following software on your system:

- [brew](https://brew.sh/), follow their installation instructions
- by installing brew you should implicitly get the apple command line tools 
- `brew update`
- `brew install sdl2 wget git`
- optional `brew install gh` to get the github command line tool for authentication and so on


First of all, you have to checkout XEMU:
```
git clone https://github.com/lgblgblgb/xemu.git
```

Go to the branch next and verify the last commit:
```
git checkout next
git log -1
```

Build XEMU:
```
make
```
After building you should find the executable in the folder `/XEMU/build/bin`. The file is named `xmega65.native`.


### Prepare the folder structure to run XEMU and develop m65dbgui
I recommend that you completely work in one folder with both, your complete XEMU installation and all resources you need to develop, run and build the m65dbgui software. Lets prepare this folder. Doing it this way make things much easier, because everything you need is kept in only on folder. It is much easier to transfer your environment between different systems or manage different configurations.

Create the folder structure with the following steps:
- Create your root folder, for example `65`
- Inside that folder create a sub-folder `xemu-next`
- Inside `xemu-next` create another subfolder named `mega65`
- Copy the XEMU executable `xmega65.native` (see above) into the folder `xemu-next`
- Inside `xemu-next` create a script with name `mega65.sh` which will be used later to start XEMU:
```
XEMU_HOME=/Users/ExampleUser/65/xemu-next
$XEMU_HOME/xmega65.native -besure -allowscanlines -showscanlines -sdlrenderquality 1 -uartmon :4510
```
- Change into the folder `mega65`. This folder holds all files e.g. ROM necessary to run XEMU
- Create an empty file named `prefdir-is-here.txt`. This file is important and makes sure that XEMU uses this folder for its configuration
- Start XEMU with the `mega65.sh` script. XEMU is displaying a series of dialogs, which you can all commit. In the end of this process you should have the folder `mega65` (your personal XEMU configuration folder) initalized and filled with standard configuration files.
- The only thing missing now are valid ROMs, go and get these ROMs from the MEGA65 Filehost and use XEMUs assistant to create an SD-card and add the ROM.
- Congratulations! Your XEMU instance from the *next* branch is up and running.

## Development Environment
Before we can develop and run *m65dbgui* you need to prepare your system to develop with *Java* and *Maven*.

### Install Java and Maven

-Download a Java JDK suitable for your system, I recommend to use the Jetbrain Runtime [JBR from here](https://github.com/JetBrains/JetBrainsRuntime/releases/tag/jbr-release-17.0.9b1087.7)
-It is better to *not* download any installer, better go and get a *tar.gz* or a *zip* which is much better to handle
-Inside `65` create another subfolder named `jdk`. Extract JBR here.
