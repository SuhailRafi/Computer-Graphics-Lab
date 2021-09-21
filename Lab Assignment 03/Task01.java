
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;

public class Task01 implements GLEventListener {
	private GLU glu;

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();		
		MidpointCircle(gl, 5, 20, 25);
		MidpointCircle(gl, 5, -30, 25);
		MidpointCircle(gl, -20, 0, 25);
		MidpointCircle(gl, 30, 0, 25);
		MidpointCircle(gl, 5, -5, 50);			
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		glu = new GLU();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glViewport(-100, -50, 50, 100);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-100.0, 100.0, -100.0, 100.0);
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
	}
	
	public void MidpointCircle(GL2 gl,  int x_new , int y_new , int radius){
		gl.glPointSize(3.0f);
		gl.glColor3d(1, 0, 0);
		gl.glBegin(GL2.GL_POINTS);
		
		int d = 1-radius;
		int x = 0;
		int y = radius;
		
		CirclePoints(gl, x, y, x_new, y_new);
		
		while(x<y){
			if(d<0){
				d = d+2*x+3;
				x = x+1;
			}
			else{
				d = d+2*x-2*y+5;
				y = y-1;
				x = x+1;
			}
			CirclePoints(gl, x, y, x_new, y_new);
		}
		
		gl.glEnd();
	}
	
	public void CirclePoints(GL2 gl, int x, int y, int x1, int y1){
		gl.glVertex2f(x+x1, y+y1);
		gl.glVertex2f(y+x1, x+y1);
		gl.glVertex2f(y+x1, -x+y1);
		gl.glVertex2f(x+x1, -y+y1);
		gl.glVertex2f(-x+x1, -y+y1);
		gl.glVertex2f(-y+x1, -x+y1);
		gl.glVertex2f(-y+x1, x+y1);
		gl.glVertex2f(-x+x1, y+y1);
	}
	
	public static void main(String[] args) {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Task01 T = new Task01 ();
		glcanvas.addGLEventListener(T);
		glcanvas.setSize(400, 400);

		final JFrame frame = new JFrame("Lab Assignment 03");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
	} 
}
