# Text phasmophobia

The goal is to create a puzzle game in text form to run in a console and the player enters commands to it. 
My project is supposed be a simplified version of Phasmophobia that can be played in this format.

It is recommended to run this game with **Windows Terminal** if you don't have it yet (It is the default in Win11). Or via intellij IDEA.

## How to play

You can download an executable from the releases and there you can find out more information about the features to the game.

You can also run this game with intellij IDEA and open the repository in there.

To run my game go to `src` / `textPhasmophobia` / `ui` / `TextUI`
Specifically you need to run the main class `TextUI` in there.

There is a map.png to visualize the layout of the map.
The game also tells what rooms the player can move to at any point.

The player should read through tutorial before playing and reading what the game tells you.
There is a small walkthrough in tutorial in the game itself.
There is a longer walkthrough below this text.

The idea of the game is explained in the app itself but here it is briefly.

In this game the player has to investigate a haunted house and figure what type of ghost is haunting it.
The player can do that by using different items found in the truck (starting position).
They are used in the ghost favourite room. Which is randomly chosen when the user starts an investigation.
There are multiple types of evidence and each ghost has 3 of them which are randomly chosen also.
The player is to use different items to test if that is a type of evidence for the ghost.

## Example walkthrough:

### command          | explanation


- tutorial         | Give the player introduction to how the game works and explain all possible commands
- start            | Start an investigation. This is the actual gameplay
- unlock house     | Use a key to unlock a door to a house
- take thermometer | the usage of these items will be displayed when they are picked up for the first time.
- take emf reader
- take dots projector
- inventory         | See the items you have. You can have max 3 items
- equip thermometer | Show temperature in every room that the player goes to. Easier to find the ghost room
- go foyer          | start moving in the house. Possible rooms to go to are displayed as a result of this command
- go bathroom       | You are looking for a room with temperature below 15 celsius
- go foyer
- go living room
- go basement
- go living room
- go kitchen         | You may need to explore all the house to find where to ghost is. The layout is shown in the map.png
- drop dots projector| place on the ground. Only way for this to work
- drop emf reader    | Can be used from hand or dropped on the ground because it makes a noise
- drop thermometer   | Leave this here too.
- journal            | check what evidence I have found and what the ghost type could be
- learn ghost        | list all ghost types and their evidence
- go truck           | grab more items. The possible ghost types
- take writing book
- take video camera
- take spirit box    | now head back to ghost room
- go foyer
- go living room
- go kitchen
- drop writing book  | Now just check for more evidences
- use spirit box     | Some evidence might come from emf reader or dots you dropped
- use video camera
- observe            | Wait for ghost to interact if you did not get all 3 evidence yet
- go truck           | Once you get all 3 evidcence.
- finish             | Now you can start a new investigation which will be different or just stop
