# Arcade-Simulator
This program stores the various games in an arcade. The data is stored in a binary tree, and can be updated.

The database of games at this arcade is a binary tree, and several changes can be made to initial data. You can add a game, remove a game, check to see if a certain game is in the arcade, update a value of a specific game, or sort the games in ascending or descending order. Each game has a name, a highscore, the initials of the player with the highscore, the total number of plays, and the revenue of this particular machine. The program prints a description of the arcade at the end of the updates in the file cidercade.dat.

**Input File** 

Input files need to have the following format on each line:
[name of game], [high score], [high score player's initials], [number of plays], [revenue in the format $XXX.XX]

**Update File**

The update file can have several different commands, and each has a unique line structure:
Command 1 is the add command, which addes a game. To use this command use the following syntx:
1 "[name of game]" [high score] [high score play's initials] [number of plays] [revenue in the format $XXX.XX]

Command 2 is the search command, which checks to see if a given game is in the arcade. This command uses the following syntax:
2 [name of game]

Command 3 is the edit command, which lets you change the value of a certain field of a certain game. This command uses the following syntx:
3 "[name of the game] [number of the field that needs to be changed] [the updated value]

Command 4 is the delete command, which removes a game from the arcade. It uses the following syntax:
4 [name of the game]

Command 5 is the sort command, which prints a list of all the games in the arcade sorted in either ascending or descending order.
The 2 options for sorting:
5 asc
5 des
