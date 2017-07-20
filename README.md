# Experiment - JNativeHook: Robot Clicker 
 Robot project, to record click positions
 
## How to Execute:

    mvnw exec:java


## How to use:
 
##### Record a position:
Click at any position on screen, and press <kbd>PRINTSCREEN</kbd>. This will save the position in a List. 

##### Run Robot
Press <kbd>PAUSE</kbd> to Start/Stop the execution. It will click between all recorded positions with a small pause. (fixed with 43ms)

##### Positions:
All positions are saved on points.txt file with the axys coordinates (x,y). This able to save positions so we can restart the execution without loosing position.

## TO DO:
- Customize timeout
- Code refactor
- Shortcut to clean all positions

## DONE
- [OK] Refresh points instance in runtime execution