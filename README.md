# Cards

This module contains all the cards available in this game.

See [Matag: The Game](https://github.com/MatagTheGame/matag-the-game/wiki) wiki


## Automated Build

![Java CI with Maven](https://github.com/MatagTheGame/matag-cards/workflows/Java%20CI%20with%20Maven/badge.svg)
 - https://github.com/MatagTheGame/matag-cards/actions


## Developing

Read [Requisites](https://github.com/MatagTheGame/game/wiki/Requisites)


## Scripting

If you are brave enough and want to add a missing card, here's some tips.

Cards are in [cards/src/main/resources](src/main/resources/cards).

They are in json format and it's a bit of a guessing game figuring out what abilities have already been implemented.
The best thing is to perform a text search on this folder for a similar card.

Few steps to follow:
 1. Create a new JsonFile with the exact name of the card.
 2. Fill just the name.
 3. Run `LinkerTest.scryFallLinker()`. This will add all basic card details (not the abilities).
 4. Populate the abilities accordingly (this is the hard part). 


After a card is coded is a very good idea to test it by importing the `cards` artifact into `game` starting the app in test mode:
 - adding the `-Dspring.profiles.active=test` profile in the maven command or intellij startup command
 - modifying the `ProdInitTestService` class by setting the board as you would like to start your test.
