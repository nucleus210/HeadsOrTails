package com.example.android.headortail;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public ImageView headOrTail;
    public Button subBetHeadsButton;
    public Button subBetTailsButton;

    /**
     * Global variables
     */
    int betValue = 0;
    int betHead = 0;
    int betTail = 0;
    int result = 50;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headOrTail = (ImageView) findViewById(R.id.headOrTail);
        subBetHeadsButton = (Button) findViewById(R.id.subBetHeadsButton);
        subBetTailsButton = (Button) findViewById(R.id.subBetTailsButton);

        /**
         * Title text color animation.
         */

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(8000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float fractionAnim = (float) valueAnimator.getAnimatedValue();

                TextView textView = (TextView) findViewById(R.id.title);
                textView.setTextColor(ColorUtils.blendARGB(Color.parseColor("#FFD62121")
                        , Color.parseColor("#FF2133D6")
                        , fractionAnim));
            }
        });

        valueAnimator.start();

        /**
         * Background color gradient animation.
         */

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.root_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        /**
         * Play music.
         */

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.arcnorth);
        MediaPlayer playsound = MediaPlayer.create(MainActivity.this, R.raw.arcnorth);
        mp.start();

        /**
         * Set ImageView transparent. Second layer over main background.
         */

        ImageView myImage = (ImageView) findViewById(R.id.main_background);
        myImage.setAlpha(55); //value: [0-255]. Where 0 is fully transparent and 255 is fully opaque.

        subBetHeadsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                betHead = betValue;
                MainActivity.this.onClick();
            }
        });

        subBetTailsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                betTail = betValue;
                MainActivity.this.onClick();
            }
        });
    }

    /**
     * This method is called when increment button is clicked.
     */

    public void incrementBet(View view) {

        if (result != 0) {
            betValue = betValue + 5;
            result = result - 5;
            displayBetValue(betValue);
            displayCash(result);
        } else {
            displayMessage("No more money!");
        }
    }

    /**
     * This method is called when decrement button is clicked.
     */

    public void decrementBet(View view) {
        if (betValue <= 0) {
            displayMessage("Invalid Bet!");
        } else {
            betValue = betValue - 5;
            result = result + 5;
            displayBetValue(betValue);
            displayCash(result);

        }
    }

    /**
     * This method is called when reset button is clicked.
     */

    public void resetBet(View view) {
        result = betValue + result;
        betValue = 0;
        displayCash(result);
        displayBetValue(betValue);
    }

    /**
     * This method is called when double button is clicked.
     */

    public void betDoubling(View view) {
        int d;
        int r;
        d = betValue;
        r = result;

        if (d <= (r - (d * 2))) {
            r = r - d;
            d = d * 2;
            result = r;
            betValue = d;

            displayCash(result);
            displayBetValue(betValue);

        } else {
            displayMessage("You have not enough cash! ");
            displayCash(result);
            displayBetValue(betValue);

        }
    }

    /**
     * This method is called when All In button is clicked.
     */


    public void allIn(View view) {
        betValue = result + betValue;
        result = 0;
        displayBetValue(betValue);
        displayCash(result);
    }

    /**
     * This method is called when choice is made between Head or Tail.
     */

    public void onClick() {
        boolean isHead;
        Random rnd = new Random();
        isHead = rnd.nextBoolean();
        Anim anim = new Anim();
        while (betValue == 0) {
            displayMessage("Make a Bet!");
            return;
        }

        anim.zoom();

        if (isHead) {
            displayMessage("Heads");
            headOrTail.setImageResource(R.drawable.headscoin);
            if (betHead != 0) {
                displayCash(result = result + (betHead * 2));
                displayWinOrLose("You Win!");
                betHead = 0;
            } else {
                displayCash(result = result - betTail);
                displayWinOrLose("You Lose!");
                if (result <= 0) {
                    displayMessage("Game Over! Try again.");
                    betValue = betTail = betHead = 0;
                    result = 50;
                    displayCash(result);
                    displayBetValue(betValue);
                }
            }
        } else {

            headOrTail.setImageResource(R.drawable.tailscoin);
            displayMessage("Tails");
            if (betTail != 0) {
                displayCash(result = result + betTail);
                displayWinOrLose("You Win!");
                betTail = 0;
            } else {
                displayCash(result = result - betHead);
                displayWinOrLose("You Lose!");
                if (result <= 0) {
                    displayMessage("Game Over! Try again.");
                    betValue = betTail = betHead = 0;
                    result = 50;
                    displayCash(result);
                    displayBetValue(betValue);
                }
            }

        }
    }

    /**
     * This method displays the given text on the screen.
     */

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.message_box);
        priceTextView.setText(message);
    }

    /**
     * This method displays the given value on the screen.
     */

    private void displayBetValue(int number) {
        TextView betHeadTextView = (TextView) findViewById(R.id.bet_value);
        betHeadTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */

    private void displayCash(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.cash_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */

    private void displayWinOrLose(String message) {
        TextView winOrLoseTextView = (TextView) findViewById(R.id.win_or_lose);
        winOrLoseTextView.setText(message);
    }

    /**
     * Animations
     */

    public class Anim extends android.view.animation.Animation {


        public void zoom() {
            ImageView image = (ImageView) findViewById(R.id.headOrTail);
            android.view.animation.Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.myanimation);
            image.startAnimation(animation1);
        }
        public void coinFlip() {
            AnimationDrawable coinAnimation;
            ImageView coinImage = (ImageView) findViewById(R.id.headOrTail);
            coinImage.setBackgroundResource(R.drawable.coin_flip);

            coinAnimation = (AnimationDrawable) coinImage.getBackground();
            coinAnimation.start();
        }
    }
}



