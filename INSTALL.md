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

### Prepare folder structure to run XEMU and develop m65dbgui
I recommend that you completely work in one folder with both, your complete XEMU installation and all resources you need to develop, run and build the m65dbgui software. Lets prepare thhis folder.