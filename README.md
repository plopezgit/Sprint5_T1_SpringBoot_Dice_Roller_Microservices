# Dice roller microservices

### Current trace

![current trace](dice-roller-microservices-use-trace.gif)

#### Requirement

##### Level 1

The dice game is played with two dice. If the result of the sum of the two dice is 7, the game is won, otherwise it is lost. A player can see a list of all the rolls he/she has made and the success percentage.
In order to play the game and make a roll, a user must register with a non-repeating name. Upon creation, it is assigned a unique numeric identifier and a registration date. If the user so wishes, you can not add any name and it will be called "ANONYMOUS". There can be more than one "ANONYMOUS" player.
Each player can see a list of all the rolls they have made, with the value of each die and whether or not they won the game. In addition, you can know your success percentage for all the rolls you have made.
You cannot delete a specific game, but you can delete the entire list of rolls for a player.
The software must be able to list all the players in the system, the success percentage of each player and the average success percentage of all the players in the system.
The software must respect the main design patterns.

- You must take into account the following construction details:

• URLs:
• POST: /players: Create a player.
• PUT /players: modify the player's name.
• POST /players/{id}/games/ : A specific player rolls the dice.
• DELETE /players/{id}/games: deletes the player's rolls.
• GET /players/: returns the list of all the players in the system with their average success rate.
• GET /players/{id}/games: returns the list of games played by a player.
• GET /players/ranking: returns the average ranking of all the players in the system. That is, the average percentage of successes.
• GET /players/ranking/loser: returns the player with the worst hit percentage.
• GET /players/ranking/winner: Returns the player with the worst hit percentage.

- Phase 1
• Persistence: Use MySQL as the database.

- Phase 2
• Change everything you need and use MongoDB to persist data.

- Phase 3
• Add security: Include JWT authentication on all accesses to microservice URLs.


##### Level 2

• Add unit, component and integration tests to the project with jUnit, AssertJ or Hamcrest libraries.
• Add Mocks to project testing (Mockito) and Contract Tests (https://docs.pact.io/)

##### Level 3

• Design and modify the project by diversifying persistence to use two database schemas at the same time, MySQL and Mongo.


## Feedback and results

Feedback soon...