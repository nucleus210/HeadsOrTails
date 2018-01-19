Simple coin Heads Or Tails game.

Features:
* animated background;
* support normal and large screens;
* Animated coin - only zoom at the moment. To do: rotated zoom coin animation.
* game is simple and you can bet on Head or Tail. Application generated random Boolean and respond with 0(heads) and 1(tails). For this is used nextBoolean method;
* added "All In" and "x2" buttons;
* added "+" and "-" buttons to increment (+5) and decrement (-5) user bet;
* added play music - but have to be fixed. Work not so well.
* added ValueAnimator - to create animation on Title text. Work and uses gradient changing animation.


To Do:

Bug: Right now when application change screen orientation reset game and start again. When added android:configChanges="orientation|screenSize" in Android Manifest file when orientation has change game still continue but load wrong layout file. Without this attribute work well but when orientation change game is restarted.

* coin flip animation;
* button click sound;
* Win or Loose sounds;
* Improve app design;
