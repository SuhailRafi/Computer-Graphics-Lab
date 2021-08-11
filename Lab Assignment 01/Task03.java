import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import java.lang.Math;
import javax.swing.JFrame;

class ThirdGLEventListener implements GLEventListener {
/**
 * Interface to the GLU library.
 */
private GLU glu;

/**
 * Take care of initialization here.
 */
public void init(GLAutoDrawable gld) {
    GL2 gl = gld.getGL().getGL2();
    glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glViewport(-250, -150, 250, 150);
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluOrtho2D(-250.0, 250.0, -150.0, 150.0);
}

/**
 * Take care of drawing here.
 */
public void display(GLAutoDrawable drawable) {
    final GL2 gl = drawable.getGL().getGL2();
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    DDA (gl, 0, 50, 0, -50);
    pixelGap (gl, 50, 50, -50, 50);
}

public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
}

public void displayChanged(GLAutoDrawable drawable,
        boolean modeChanged, boolean deviceChanged) {
}

public void dispose(GLAutoDrawable arg0)
{
 
}

public int getZone (int dx, int dy) {

    if (dx >= 0 && dy >= 0) {
        dx = Math.abs (dx);
        dy = Math.abs (dy);
        if (dx >= dy) return 0;
        return 1;
    }
    else if (dx <= 0 && dy >= 0) {
        dx = Math.abs (dx);
        dy = Math.abs (dy);
        if (dx >= dy) return 3;
        return 2;
    }
    else if (dx <= 0 && dy <= 0) {
        dx = Math.abs (dx);
        dy = Math.abs (dy);
        if (dx >= dy) return 4;
        return 5;
    }
    else {
        dx = Math.abs (dx);
        dy = Math.abs (dy);
        if (dx >= dy) return 7;
        return 6;       
    }
}

public int ZoneZeroX (int x, int y, int zone) {
    switch (zone) {
        case 1: return y;
        case 2: return y;
        case 3: return -x;
        case 4: return -x;
        case 5: return -y;
        case 6: return -y;
        default: return x;
    }
}

public int ZoneZeroY (int x, int y, int zone) {
    switch (zone) {
        case 1: return x;
        case 2: return -x;
        case 4: return -y;
        case 5: return -x;
        case 6: return x;
        case 7: return -y;
        default: return y;
    }
}

public void realZone (GL2 gl, int x, int y, int zone){
    switch (zone) {
        case 1:
            gl.glVertex2d (y,x);
            break;
        case 2:
            gl.glVertex2d (-y,x);
            break;
        case 3:
            gl.glVertex2d (-x,y);
            break;
        case 4:
            gl.glVertex2d (-x,-y);
            break;
        case 5:
            gl.glVertex2d (-y,-x);
            break;
        case 6:
            gl.glVertex2d (y,-x);
            break;
        case 7:
            gl.glVertex2d (x,-y);
            break;
        default:
            gl.glVertex2d (x,y);
    }
}

public void DDA (GL2 gl, int x1, int y1, int x2, int y2) {

    gl.glPointSize (2.0f);
    gl.glBegin (GL2.GL_POINTS);

    int dx = (x2 - x1);
    int dy = (y2 - y1);
    int zone = getZone (dx, dy);
    int ddx, ddy, zx1, zx2, zy1, zy2, x, y;
    
    zx1 = ZoneZeroX(x1, y1, zone);
    zy1 = ZoneZeroY(x1, y1, zone);
    zx2 = ZoneZeroX(x2, y2, zone);
    zy2 = ZoneZeroY(x2, y2, zone);
    
    ddx = (zx2 - zx1);
    ddy = (zy2 - zy1);
    
    int D = (2 * ddy) - ddx;
    int NE = 2 * (ddy - ddx);
    int E = 2 * ddy;
    
    x = zx1;
    y = zy1;
    
    while (x <= zx2) {
        realZone (gl,x,y,zone); x++;
        if (D > 0) { y++; D = D + NE; }
        else D = D + E;
    }
    
    gl.glEnd();
}

public void pixelGap (GL2 gl, int x1, int y1, int x2, int y2) {

    gl.glPointSize (2.0f);
    gl.glBegin (GL2.GL_POINTS);

    int dx = (x2 - x1);
    int dy = (y2 - y1);
    int zone = getZone (dx, dy);
    int ddx, ddy, zx1, zx2, zy1, zy2, x, y;
    
    zx1 = ZoneZeroX(x1, y1, zone);
    zy1 = ZoneZeroY(x1, y1, zone);
    zx2 = ZoneZeroX(x2, y2, zone);
    zy2 = ZoneZeroY(x2, y2, zone);
    
    ddx = (zx2 - zx1);
    ddy = (zy2 - zy1);
    
    int D = (2 * ddy) - ddx;
    int NE = 2 * (ddy - ddx);
    int E = 2 * ddy;
    
    x = zx1;
    y = zy1;
    
    while (x <= zx2) {
        realZone (gl,x,y,zone); x+=10;
        if (D > 0) { y+=10; D = D + NE; }
        else D = D + E;
    }
    
    gl.glEnd();
}


}
public class Task03
{
public static void main(String args[])
{
 //getting the capabilities object of GL2 profile
 final GLProfile profile=GLProfile.get(GLProfile.GL2);
 GLCapabilities capabilities=new GLCapabilities(profile);
 // The canvas
 final GLCanvas glcanvas=new GLCanvas(capabilities);
 ThirdGLEventListener b=new ThirdGLEventListener();
 glcanvas.addGLEventListener(b);
 glcanvas.setSize(400, 400);
 //creating frame
 final JFrame frame=new JFrame("Basic frame");
 //adding canvas to frame
 frame.add(glcanvas);
 frame.setSize(640,480);
 frame.setVisible(true);
}
}