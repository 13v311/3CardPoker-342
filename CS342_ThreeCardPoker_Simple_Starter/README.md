# CS 342 Fall 2026 - Three Card Poker Project

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

## Where to add your work
- `src/main/java/`: Java application, controllers, model, and game logic
- `src/main/resources/edu/uic/cs342/poker/fxml/`: FXML layouts
- `src/main/resources/edu/uic/cs342/poker/css/`: CSS stylesheets
- `src/main/resources/edu/uic/cs342/poker/images/`: images and other assets
- `src/test/java/`: JUnit tests

The starter does **not** implement poker rules, betting, additional screens,
animations, or final UI styling. These are student responsibilities.
### Color Palette
#1D1D1E Deep Charcoal
#78142C Maroon
#C6953F Antique Gold
#0B403A Deep Forest Teal
#E9E3D6 Warm Cream
#F9F7F5 Warm Off-White
*these possibly change in shade on the buttons, but this is the main palette*

## Design Reflection
### 1. Why did you separate game logic from the FXML controllers?
### 2. What usability problems did other students identify?
### 3. Which wireframe revisions most improved the user experience?
Adding boxes around each user's sections in both the Results and Gameplay portions of the interface help users to better distinguish
the information! Additionally, we added a Rules page that originally did not exist, as well as the ability to toggle between light and dark mode throughout the program.
### 4. How do the animations help users understand game events?
### 5. How could the interface be redesigned without changing the game rules?
Having a `ThemeManager` class that simply changes the scene's stylesheets is incredibly helpful in accomplishing this goal.
Since the scene's stylesheet is changed, it only affects how the interface appears, but not any particular logic. Additionally,
we ensure that the scene stays the same rather than replacing the scene with an entirely new one.
### 6. How does your architecture make the application easier to test and maintain?