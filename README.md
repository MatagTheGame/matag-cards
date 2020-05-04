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

Cards in json format and it's a bit of a guessing game figuring out what abilities have already been implemented.
The best thing is to perform a text search on this folder for a similar card.

After adding the card do not forget to add it as well on the related sets.



When a card is added the `imageUrls` can be removed from the json and automatically fetched from the server by running
`CardsTests.cardScryFallLinker()` test.



After a card is coded is a very good idea to test it by importing the `cards` artifact into `game` starting the app in test mode:
 - adding the `-Dspring.profiles.active=test` profile in the maven command or intellij startup command
 - modifying the `ProdInitTestService` class by setting the board as you would like to start your test.
