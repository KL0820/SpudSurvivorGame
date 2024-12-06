package org.ntut.posd2024.finalproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpudSurvivorGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private Player player;
    private Enemy enemy;
    private String message;
    private boolean enemyDefeated;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Use LibGDX default font

        // Initialize player and enemy
        player = new Player("Brave Potato", 100, 25);
        enemy = new Enemy("Evil Carrot", 80, 15);

        // Initial game message
        message = "You encountered " + enemy.getName() + "!\nChoose your action:\n1. Attack\n2. Defend\n3. Run Away";
        enemyDefeated = false;
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the game message
        batch.begin();
        font.draw(batch, message, 20, Gdx.graphics.getHeight() - 20);
        batch.end();

        // Handle player input
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { // Attack
            int damage = player.attack();
            enemy.takeDamage(damage);
            if (enemy.getHealth() <= 0) {
                message = "You defeated " + enemy.getName() + "! Congratulations!";
                enemyDefeated = true;
            } else {
                enemyAttack();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) { // Defend
            message = "You chose to defend and reduced the enemy's attack damage!";
            int reducedDamage = Math.max(0, enemy.getAttackPower() - 5);
            player.takeDamage(reducedDamage);
            enemyAttack();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) { // Run Away
            message = "You ran away, losing 10 health points, but escaped the battle.";
            player.takeDamage(10);
            Gdx.app.exit(); // Exit the game
        }

        // Check game over condition
        if (player.getHealth() <= 0) {
            message = "You have been defeated! Game Over.";
            Gdx.app.exit();
        }
    }

    private void enemyAttack() {
        if (!enemyDefeated) {
            int damage = enemy.getAttackPower();
            player.takeDamage(damage);
            message += "\n" + enemy.getName() + " attacked you! Your current health is " + player.getHealth() + ".";
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
