# COMP2042 Resit Coursework

Ethan Lim Gzi Yaoo
20499680

# Github Repository
https://github.com/ethanlim05/CW.git

# Compilation Instructions

## Prerequisites
1. Clone the repository:
https://github.com/ethanlim05/CW.git
2. Open the cloned repository in IntelliJ IDEA.
3. Install the JavaFX Library, https://gluonhq.com/products/javafx/ and unzip it to a desired location.

## Setup
1. Go to File > Project Structure or press Ctrl + Alt + Shift + S.
2. In the Project Settings > Project section:
    - Set the project SDK to 21 or greater.
    - Set the language level as sdk default.
3. In the Project Settings > Libraries section:
    - Add a new project library that points to the lib folder of the JavaFX SDK you installed.
4. Ensure the JavaFX classes are recognized by IntelliJ IDEA.

## Running the Project
1. In the src folder, locate the Main class file in the com.example.demo.controller package.
2. Run the main method within the Main class.
3. Maven will build the project, and the application will start running.

# Implemented and Working Properly

## Fixed Bugs
- Fixed score saving issue: Resolved a bug where scores were not being saved correctly between game sessions.
- Fixed username validation: Added proper validation to prevent empty or duplicate usernames.
- Fixed fullscreen mode: Implemented proper fullscreen functionality that scales the game board appropriately.
- Fixed tile positioning: Resolved issues where number boxes were appearing outside the grid boundaries.
- Fixed text centering: Corrected problems where numbers were jumping outside their tiles during animations.

## General Features
- Smooth Animations: Implemented smooth animations for tile movements and merges without changing gameplay mechanics.
- Fullscreen Mode: The game can be played in fullscreen mode by clicking the "Play" button. Press ESC to exit fullscreen.
- Account Management: Users can create accounts with unique usernames and save their scores.
- Leaderboard System: Displays top 10 player scores sorted from highest to lowest.
- Game Board: Implemented a 4x4 grid with proper tile movement and merging mechanics.
- Score Tracking: Tracks and displays the player's score during gameplay.
- Game States: Properly handles game states including active gameplay, game over, and win conditions.
- Main Menu: Implemented a main menu with options to play, view account, check leaderboard, and quit.
- Responsive UI: The game interface adapts to different screen sizes, especially in fullscreen mode.

## Game Features
- Tile Movement: Players can move tiles using arrow keys (Up, Down, Left, Right).
- Tile Merging: When two tiles with the same value collide, they merge into a single tile with double the value.
- Random Tile Generation: After each move, a new tile (2 or 4) randomly appears in an empty spot.
- Win Condition: The game is won when a tile with the value 2048 appears.
- Game Over Detection: The game ends when no more valid moves are possible.
- Score Calculation: Points are awarded when tiles merge, with the value of the new tile added to the score.
- Animated Tile Appearance: New tiles now appear with a smooth scaling animation.
- Animated Tile Movement: Tiles smoothly slide to their new positions when moved.

# Implemented but Not Working Properly

## Bugs
- Fullscreen Scaling Issues: In some screen resolutions, the game board doesn't scale perfectly, causing slight misalignment of tiles.
- Debugging Attempts: Tried different scaling approaches but couldn't achieve perfect scaling on all resolutions.
- This is considered a minor issue as it doesn't affect gameplay functionality.
- Occasional Score Saving Delay: Sometimes there's a delay in saving scores to the file when returning to the main menu.
- Debugging Attempts: Added debug statements to track the saving process, but couldn't consistently reproduce the issue.
- The scores are eventually saved correctly, so this has been left as is.

# Features Not Implemented

## Planned Features
 - Undo Functionality: Allow players to undo their last move.
 - Sound Effects: Audio feedback for tile movements, merges, and game events.
 - Multiple Themes: Different color schemes and visual styles for the game.
 - Achievement System: Unlockable achievements based on gameplay milestones.

# New Java Classes

| Class Name    | Path                                                        | Description                                                                                |
|---------------|-------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| SceneManager  | src/main/java/com/example/demo/controller/SceneManager.java | Manages scene transitions between different views (main menu, game, account, leaderboard). |
| GameConstants | src/main/java/com/example/demo/model/GameConstants.java     | Contains constants used throughout the game, such as window dimensions and cell sizes.     |
| GameBoard     | src/main/java/com/example/demo/view/GameBoard.java          | Represents the game board and handles the visual representation of the grid.               |
| ScoreDisplay  | src/main/java/com/example/demo/view/ScoreDisplay.java       | Displays the current score during gameplay.                                                |
| AnimatedTile  | src/main/java/com/example/demo/view/AnimatedTile.java       | Handles the visual representation and animations of individual tiles.                      |

# Modified Java Class

## Main.java
- Added fullscreen functionality with enterFullscreen() and exitFullscreen() methods.
- Modified showGame() to automatically enter fullscreen when starting the game.
- Added ESC key handling to exit fullscreen mode.

## GameView.java
- Increased cell size from 100 to 150 pixels for better visibility.
- Increased cell spacing from 10 to 15 pixels.
- Removed the top back button, keeping only the bottom one.
- Implemented adjustToFullscreen() method to properly scale the game board in fullscreen mode.
- Implemented resetToOriginalSize() method to return to normal windowed mode.
- Moved the back button lower to prevent it from blocking the game board.

## Cell.java
- Increased base font size from 24 to 36 points.
- Modified updateDisplay() to dynamically calculate font size based on cell size.
- Implemented resizeForFullscreen() method to adjust cell size and position in fullscreen mode.
- Increased corner radius from 10 to 15 for a more rounded appearance.

## Account.java
- Added usernameExists() method to check for duplicate usernames.
- Enhanced setUsername() method with proper validation.
- Improved error handling in saveScores() and loadScores() methods.
- Added debug statements to track score saving and loading.

## AccountView.java
- Added username validation to prevent duplicate usernames.
- Enhanced error messaging for username validation.
- Improved button positioning to prevent UI overlap.

## LeaderboardView.java
- Implemented proper sorting of accounts by score.
- Added debug statements to track leaderboard loading and sorting.
- Improved visual presentation of leaderboard entries.

## Controller.java
- Added ESC key handling for fullscreen mode.
- Improved key event handling for better responsiveness.
- Simplified animation handling by moving animation logic to GameView.

## GameModel.java
- Enhanced game logic for better tile movement and merging.
- Improved game state detection (win/lose conditions).
- Added debug statements to track game state changes.

# Unexpected Problems

## Code Organization Issues
- Package Reorganization: Initially, all classes were in a single package, making the codebase difficult to navigate.
- Solution: Reorganized classes into model, view, and controller packages following MVC architecture.
- This required updating all import statements and package declarations, which was time-consuming but improved code organization significantly.

## UI Layout Problems
- Button Positioning: The "Back to Menu" button was blocking the game board.
- Solution: Moved the button lower on the screen and adjusted its position in both windowed and fullscreen modes.
- Also removed the duplicate top back button to simplify the UI.

## Fullscreen Implementation Challenges
- Scaling Issues: The initial fullscreen implementation made the game board too small.
- Solution: Revised the scaling algorithm to use more screen space and increase cell sizes.
- Added separate methods for entering/exiting fullscreen to properly handle UI elements.

## File Handling Problems
- Score Persistence: Scores were not being saved consistently between game sessions.
- Solution: Enhanced error handling in the save/load methods and added debug statements to track the process.
- Improved the file path handling to ensure consistent behavior across different systems.

## Performance Concerns
- Memory Usage: The game was using more memory than necessary, especially when switching between scenes.
- Solution: Implemented proper cleanup of UI elements when switching scenes.
- Added methods to explicitly clear and reset game state when returning to the main menu.

## Animation System Complexity
- Tile Positioning During Animation: Initial animation implementation caused tiles to jump outside their intended positions.
- Solution: Created a new AnimatedTile class with proper animation handling and containment.
- Implemented grid and cell-level clipping to ensure tiles stay within boundaries during animations.
- Simplified the animation system to use basic transitions without complex translation calculations.
