package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;

    SpriteBatch batch;
    TextureRegion DOWN, UP, RIGHT, LEFT;
    FitViewport viewport;

    float x = 0;
    float y = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    float MAX_VELOCITY = 100;
    float time = 0;

    final int DRAW_WIDTH = WIDTH;
    final int DRAW_HEIGHT = HEIGHT;

    @Override
    public void create() {
        batch = new SpriteBatch();

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        DOWN = grid[6][0];
        UP = grid[6][1];
        LEFT = grid[6][2];
        RIGHT = grid[6][3];
        LEFT = new TextureRegion(RIGHT);
        LEFT.flip(true, !false);//TROLL ASS CODE
    }

    @Override
    public void render() {
        move();
        draw();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    void move() {
        if (x >= 0 & y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {//constant value that represents the UP key
                yVelocity = MAX_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yVelocity = MAX_VELOCITY * -1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xVelocity = MAX_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xVelocity = MAX_VELOCITY * -1;
            }

            x += xVelocity * Gdx.graphics.getDeltaTime();//function that adapts to users PC frames per second
            y += yVelocity * Gdx.graphics.getDeltaTime();

            xVelocity *= .09;//dampening, adding "friction" to slow a stop!
            yVelocity *= .09;
        }

        float reX = x;
        float reY = y;

        if(x< 0 && x> (Gdx.graphics.getWidth()-WIDTH)){
            x = reX;
        }
        if(y< 0 && y>(Gdx.graphics.getWidth()-HEIGHT)){
            y = reY;
        }
    }
    void draw(){

        time += Gdx.graphics.getDeltaTime();

        TextureRegion img;
        if(Math.round(yVelocity) > Math.round(xVelocity)){
            if(yVelocity > 0){
                img = UP;
            }
            else{
                img = DOWN;
            }
        }
        else{
            if(xVelocity > 0){
                img = RIGHT;
            }
            else {
                img = LEFT;
            }
        }

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if(xVelocity >= 0){
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        }
        else {
            batch.draw(img, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }

        batch.end();
    }
}