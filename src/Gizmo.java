import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import sun.security.util.PolicyUtil;

import java.awt.*;

/**
 * Project: PinballGame
 * Author: KaitoHH
 * Create Date: 2016/11/18
 * Description:
 * All rights reserved.
 */
public class Gizmo {
	private static World world;
	private final static int size = 5;
	private static float angularResistForce = 1f;
	private static float linearResistForce = 1f;
	private static int rowNum;
	private static Body ground;
	private static BodyType move = BodyType.DYNAMIC;

	private Body body;
	private int x;
	private int y;
	private int sizeRate = 1;
	private toolBoxPanel.rotation rotate;
	private GraphPanel.Shape shape;
	private Color color;
	private double angle = 0 * Math.PI / 180;

	public static void setWorld(World world) {
		Gizmo.world = world;
		Gizmo.world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Body body1 = contact.getFixtureA().getBody();
				Body body2 = contact.getFixtureB().getBody();
				if (body2.getUserData() == GraphPanel.Shape.Ball) {
					Body b = body1;
					body1 = body2;
					body2 = b;
				}
				if (body1.getUserData() == GraphPanel.Shape.Ball && body2.getUserData() == GraphPanel.Shape.Absorber) {
					body1.setUserData(null);
					Gizmo.world.destroyBody(body1);
				}
			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void preSolve(Contact contact, Manifold manifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse contactImpulse) {

			}
		});
	}

	public static void setMove(boolean f) {
		move = f ? BodyType.DYNAMIC : BodyType.STATIC;
	}

	public static int getLength() {
		return size * rowNum;
	}

	public static void setRowNum(int rowNum) {
		Gizmo.rowNum = rowNum;
	}

	public Gizmo(int x, int y, int sizeRate, GraphPanel.Shape shape, Color color, toolBoxPanel.rotation rotate) {
		this.x = x;
		this.y = y;
		this.sizeRate = sizeRate;
		this.shape = shape;
		this.color = color;
		this.rotate = rotate;
		createBody();
	}

	public Body getBody() {
		return body;
	}

	public void createBody() {
		switch (shape) {
			case Triangle:
				addTriangle(x, y, sizeRate);
				break;
			case Rectangle:
			case Track:
			case Absorber:
				addSquare(x, y, sizeRate);
				break;
			case Slider:
				addSlider(x, y, sizeRate);
				break;
			case Circle:
				addCircle(x, y, sizeRate);
				break;
			case Ball:
				addBall(x, y);
				break;
			case Paddle:
				addPaddle(x, y);
				break;
		}
		body.setUserData(shape);
	}

	public void updateBody() {
		world.destroyBody(body);
		createBody();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSizeRate() {
		return sizeRate;
	}

	public void setSizeRate(int sizeRate) {
		this.sizeRate = sizeRate;
	}

	public GraphPanel.Shape getShape() {
		return shape;
	}

	public void setShape(GraphPanel.Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public toolBoxPanel.rotation getRotate() {
		return rotate;
	}

	public static void addSingleBoarder(int x, int y) {
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position.set(x * size * rowNum, y * size * rowNum);
		Body body = world.createBody(def);
		PolygonShape box = new PolygonShape();
		if (y == x) {
			box.setAsBox(size * rowNum, 0);
		} else {
			box.setAsBox(0, size * rowNum);
		}
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.restitution = 1;
		body.createFixture(fixtureDef);
		if (x == 0 && y == 0)
			ground = body;
	}


	public void addSquare(int x, int y, int sizeRate) {
		y = 20 - y;
		BodyDef def = new BodyDef();
		def.type = (shape == GraphPanel.Shape.Rectangle) ? move : BodyType.STATIC;
		def.gravityScale = 0;
		def.angularDamping = angularResistForce;
		def.linearDamping = linearResistForce;
		int a = sizeRate * size;
		def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
		body = world.createBody(def);
		PolygonShape box = new PolygonShape();
		box.setAsBox(a / 2.0f, a / 2.0f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 50;
		fixtureDef.restitution = 1;
		if (shape == GraphPanel.Shape.Track)
			fixtureDef.restitution = 0;
		body.createFixture(fixtureDef);
	}

	private void addSlider(int x, int y, int sizeRate) {
		y = 20 - y;
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position.set(x * size + size / 2.0f, y * size - size * 0.875f);
		body = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.5f * size, 0.125f * size);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 1.5f;
		body.createFixture(fixtureDef);
	}

	private void addCircle(int x, int y, int sizeRate) {
		y = 20 - y;
		BodyDef def = new BodyDef();
		def.type = move;
		def.gravityScale = 0;
		def.angularDamping = angularResistForce;
		def.linearDamping = linearResistForce;
		int r = sizeRate * size;
		def.position.set(x * size + r / 2.0f, y * size - r / 2.0f);
		body = world.createBody(def);
		CircleShape circle = new CircleShape();
		circle.setRadius(r / 2.0f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 50;
		fixtureDef.restitution = 1;
		body.createFixture(fixtureDef);
	}

	private void addBall(int x, int y) {
		y = 20 - y;
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		float r = size / 2.0f;
		def.position.set(x * size + r / 2.0f, y * size - r / 2.0f);
		do {
			body = world.createBody(def);
		} while (body == null);
		CircleShape circle = new CircleShape();
		circle.setRadius(r / 2.0f);
		try {
			body.createFixture(circle, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPaddle(int x, int y) {
		y = 20 - y;
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.gravityScale = 100;
		if (getRotate() == toolBoxPanel.rotation.left)
			def.position.set(x * size + 0.875f * size, y * size - size);
		else
			def.position.set(x * size + 0.125f * size, y * size - size);
		body = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.125f * size, size);
		body.createFixture(shape, 1);
		RevoluteJointDef rjd = new RevoluteJointDef();
		if (getRotate() == toolBoxPanel.rotation.left) {
			rjd.initialize(ground, body, new Vec2(((x + 0.875f) * size), y * size));
			rjd.upperAngle = 0;
			rjd.lowerAngle = -(float) (Math.PI / 2);
		} else {
			rjd.initialize(ground, body, new Vec2((x + 0.125f) * size, y * size));
			rjd.lowerAngle = 0;
			rjd.upperAngle = (float) (Math.PI / 2);
		}

		rjd.enableLimit = true;
		world.createJoint(rjd);
	}

	public void addTriangle(int x, int y, int sizeRate) {
		y = 20 - y;
		BodyDef def = new BodyDef();
		def.type = move;
		def.gravityScale = 0;
		def.angularDamping = angularResistForce;
		def.linearDamping = linearResistForce;
		int a = sizeRate * size;
		def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
		def.angle = (float) -angle;
		body = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		float r = a / 2.0f;
		shape.set(new Vec2[]{new Vec2(-r, -r), new Vec2(-r, r), new Vec2(r, -r)}, 3);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 50;
		fixtureDef.restitution = 1;
		body.createFixture(fixtureDef);
	}

	public void applyForce() {
		body.applyAngularImpulse(10000.0f * (getRotate() == toolBoxPanel.rotation.left ? -1 : 1));
	}

	public void move(int dx, int dy) {
		Vec2 position = body.getPosition();
		body.setTransform(new Vec2(position.x + dx, position.y + dy), 0);
	}
}
