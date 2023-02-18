package dk.easv.movieviewstrategy;

/**
 * Strategy interface for different movie draws.
 */
public interface MovieViewStrategy {

    /**
     * This should draw the page to the screen.
     * @param page The page number to draw.
     */
    void drawPage(int page);

}
