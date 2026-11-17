# CS 342 Fall 2026 - Three Card Poker Project
# Team MiniPekka >:)
## Members
David Flores & Levell Kensey

![mini pekka img](https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/5a6af839-076e-448b-b7e8-47dcfb1f1af3/dgew8g0-3bb40d87-19df-4b09-8c22-e25fa348ff27.png/v1/fill/w_894,h_894/mini_pekka_render_by_henukim_dgew8g0-pre.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDA5NiIsInBhdGgiOiIvZi81YTZhZjgzOS0wNzZlLTQ0OGItYjdlOC00N2RjZmIxZjFhZjMvZGdldzhnMC0zYmI0MGQ4Ny0xOWRmLTRiMDktOGMyMi1lMjVmYTM0OGZmMjcucG5nIiwid2lkdGgiOiI8PTQwOTYifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.5stibFKlkjGePUUkFKQQxKSIbOs6_hfmqVpXoT8QKxY)

## Requirements
- JDK 25
- Maven 3.9+ (or compatible)

## Run
```bash
mvn clean javafx:run
```

## Test
```bash
mvn test
```
And you can view what the tests are actually looking for within the files of the test folder.

### Color Palette
- #1D1D1E Deep Charcoal
- #78142C Maroon
- #C6953F Antique Gold
- #0B403A Deep Forest Teal
- #E9E3D6 Warm Cream
- #F9F7F5 Warm Off-White

*these possibly change in shade on the buttons and background between light/dark mode, but this is the main palette*

## Individual Contributions
- Levell: Worked on UI logic, including implementing FXML and Controllers, CSS, and connecting everything to the GameService. Also wrote the majority of this README!
- David: Worked on game logic, including Model Classes, GameService, game logic objects,  and testing the game logic. Also developed the UML Diagram.

## Major Software Component Responsibilities

### Controllers
Controllers such as `GameController`, `ResultsController`, etc., are meant to separate the UI (user interface, which is what you as a user see) from the application logic (not the game logic). Looking at the code within the controllers, users will find many methods that are played when clicking buttons, or watching animations.

### Service
`GameService` is the Service we use for this project, and it is what handles the game logic. Wagers, folding, playing, etc., are all defined here.

### GameLogic
In the GameLogic folder, you will find classes that are used by `GameService`, such as `ThreeCardLogic`, `Player`, and `Dealer`. As the folder name suggests, they represent the backbone of the game logic. Besides `GameService`, these classes are also sparsely used in some fxml files.

### Other Classes
There are some other classes such as `ThemeManager` and `SceneSwitcher` which are useful for the fxml to change scenes and CSS themes. `Main` is also important as it boots up the program.

### Model Classes
Used to represent objects of the game, such as `Card` and `Dealer`.

### Stylesheets
All the stylesheets which control how the UI appears visually are in the CSS folder.

### FXML
The fxml is the backbone for the UI. Every screen you see is produced by the combination of a fxml class and the controller.


### Testing
We have tests for different parts of the game logic defined in the Test folder. For example, `DeckAndDealerTests` will test the functions that are used by the deck/dealer.

## Each Screen

### Welcome / Main Menu
This is the starting screen when the program loads, and allows students to either start playing or read the rules first to get acclimated.

### Rules
This is a helpful screen to get users familiar with how to play.

### Gameplay
This screen is where most of the action happens, as in the gameplay. It’s meant to allow students to interact with the game. They can make wagers, view their hand, and any other important parts of a three-card poker game.

### Results
This screen is meant to show the full description of results for the game that was played. Major details include wager values (play, pair plus, ante), winnings, total values, player decisions (folded/played). It is also meant to serve as a transition point between returning to the main menu or continuing the game.

## All Animations

### `SequenceTransition`
This is the overall transition that is used for both FadeTransition and TranslateTransition, but the focus is more on the transitions it encompasses. It is necessary to mention, though, because it is what allows our transitions to play in chronological order.

### `FadeTransition`
This transition is to fade out the back of the cards and fade in the front of the cards. This is first done for the players after their wagers are made, and is then done for the dealer after the players make their move.

### `TranslateTransition`
This transition is played at the very start of the game, where each card starts from the deck and moves either to the dealer or one of the players. The order goes dealer’s → player 1’s → player 2’s.

### `ScaleTransition`
This transition is played when players continue the game, to show that their total and winnings have updated. It’s played on the labels that show those values.

## Changes After Usability Testing

- We added boxes around each player’s section. This will make it easier for each player to distinguish their section. We also changed the formatting of the balance and winnings, as it was unclear which one was the total. We also moved the “Deal” button because before it seemed that each player was playing independently of the others, but in reality they are playing simultaneously.
- We again added boxes around each user's section to better distinguish the information. We also made the overall results box larger to provide more space to fit the information. Previously, it was too crowded, overwhelming users.
- Before taking a student’s review, we did not have a rules page and an option for a secondary theme. After reading their review, we added a rules page, and we also added a ‘dark mode’, with the option to change the theme from light to dark / dark to light at any point by clicking the ‘Theme’ button in the top right corner.


## Design Reflection
### 1. Why did you separate game logic from the FXML controllers?
Separating the game logic from the FXML controllers keeps the program organized and separates multiple responsibilities. Keeping them independent makes it easier to use them together and to make any changes to any one of them.
### 2. What usability problems did other students identify?
A major usability problem other students found was that our design wasn’t good at distinguishing the information presented on screen. Another problem was that the placement and organization of many fields were confusing, which overwhelmed them. The third problem was that the design for the amount of money and winnings was confusing. All these problems came down to our design being unclear, leaving them confused and overwhelmed.
### 3. Which wireframe revisions most improved the user experience?
Adding boxes around each user's sections in both the Results and Gameplay portions of the interface help users to better distinguish
the information! Additionally, we added a Rules page that originally did not exist, as well as the ability to toggle between light and dark mode throughout the program.
### 4. How do the animations help users understand game events?
One example of an animation we use is the `FadeTransition` animation. This animation is played when revealing player’s cards and dealer’s cards. It helps the users understand that they are now at a point where they can decide if they want to play their cards or fold, as well as get an idea of what cards they have. The cards are not revealed until after wagering, which shows a clear distinction between the points of the game.
### 5. How could the interface be redesigned without changing the game rules?
Having a `ThemeManager` class that simply changes the scene's stylesheets is incredibly helpful in accomplishing this goal.
Since the scene's stylesheet is changed, it only affects how the interface appears, but not any particular logic. Additionally,
we ensure that the scene stays the same rather than replacing the scene with an entirely new one.
### 6. How does your architecture make the application easier to test and maintain?
Because the responsibilities are split into separate classes, it’s easy to test our program. If we want to test a specific part of the program, we only focus on that part. If the architecture weren’t like this, then we would have to deal with multiple parts when it comes to testing. Splitting it into different parts also makes it easy to maintain, as each responsibility is independent. Changing something is easy, as there are no dependent parts.