# Space-Invaders-Java-version-
This project is a Java-based recreation of the classic Space Invaders arcade game, built using JFrame and JPanel for the graphical interface.
The player controls a spaceship at the bottom of the screen, moving left and right to dodge and shoot at waves of invading aliens.

Key features of the game:

-Player Ship: A movable spaceship controlled by keyboard input.

-Aliens: Multiple rows of animated aliens with different colors that move horizontally and shift downward when they hit the screen edges.

-Bullets: Players can shoot bullets upward to eliminate the aliens.

-Scoring System: Points are awarded for each alien defeated. Bonus points are given when all aliens in a level are cleared.

-Increasing Difficulty: After clearing a level, more aliens spawn, and the game progressively becomes harder.

-Game Over Condition: The game ends when any alien reaches the player's spaceship row.

-Simple Graphics: Game assets are loaded from external image files to represent the spaceship and aliens.

-Keyboard Controls:

Left/Right Arrows (←, →) to move.

J key to shoot bullets.

The game loop runs at 60 frames per second using a Timer, managing player input, alien movement, collision detection, and screen rendering.
