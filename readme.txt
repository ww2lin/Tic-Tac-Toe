
Program:
Using a Model-View-Controller (MVC) architecture, created a Java Swing program 
to play the classic 3 x 3 tic-tac-toe game. When someone wins the button will
turn green to show the wining line


Enhancements:
	all the options below can be change through the interface
	-board size
		can scale the board size to a bigger board
		it is hardcoded to 8(for the upperbound)
		but can easily be change inside the source code

	-wining constraint
		the defualt rule to win is to get a 3 in a row.
		but this can be change up to the board size
		(note: if winConstraint > current selected boardSize then the
		game will aways tie, this is an intended behaviour)

	-AI
		2 simple computer
			-random (places random piece)
			-greedy (where it tries to get most points)
		user can select "two player" it he/she does not want
		to play with computers
