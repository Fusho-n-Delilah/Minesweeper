# Minesweeper
A Minesweeper Clone written in Java

This clone uses an MVC with ViewController design. The Model consists of a Board with a private class of a Cell. The board is a two-dimensional array that when initialized places all the bombs in random cells. The View (Java swing GUI) calls the controller to handle the click. eg. if it is a right click it sets the flag, and so that it will do nothing to that cell if left clicked later. these flags of isBomb and isFlagged are handled on the model and called by the controller. The boards visual state is then passed to the View through the view controller and the view is reinitialized with the current game state. There is a functioning timer, changing difficulty results in a larger game board with the appropriate number of bombs per the original games specs. 

The view uses sprites  for all the numbers various smiley faces etc. Beat it on the hardest difficulty for a special surprise!

<img width="414" alt="image" src="https://github.com/user-attachments/assets/26c27875-a61c-4941-a294-57f68254ab8b" />

Thanks for checking out my project!

