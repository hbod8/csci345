# Cohesion

## Game

Game uses Procedural Cohesion as it manages the setup and step-by-step calling of functions to control player's turns and player actions.  It resembles some Logical Cohesion in the calling of Player functions in Player.

## Player

Player uses Logical and Functional Cohesion by managing what action the player will take (Logical) and allowing the access and augmentation of player data (Functional).

## Role, Scene, Room, and SetRoom

All these classes display Functional Cohesion because they only include specific functions that all serve the single purpose of the class.

# Coupling

## Game and Player

Game and Player share a significant amount of data and therefore have some instances of content coupling but mainly use data coupling.  The instance of content coupling is boolean Player.inScene and Game repeatedly calling Player.act() and various other player actions based on user input in Game.

## Role, Room, Scene, and SetRoom

These classes use only use data coupling as it only implements getters and setters and does not violate information hiding.

# Design Patterns

## Model View Controller

We heavily relied on the MVC design pattern throughout our implementation of deadwood.  Our 3 packages reflect this idea as each package reflects one of the structures.

## Adapter

Although not fully implemented, while making our conroller class we thought about how portable it would be if we wanted to change the view.  We coudld even return too a commandline implementation without changing GameController.java or the model.