/*
 * ImageUIResource.java
 *
 * Created on November 8, 2007, 6:42 PM
 */

package mdes.slick.sui.skin;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author davedes
 */
public class ImageUIResource extends Image implements UIResource, java.io.Serializable {
    //TODO: add more constructors
    public ImageUIResource(String ref) throws SlickException {
        super(ref);
    }
}