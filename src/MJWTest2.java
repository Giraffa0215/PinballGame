import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class MJWTest2 extends TestbedTest {

	@Override
	public void initTest(boolean argDeserialized) {
		getWorld().setGravity(new Vec2(0.0f, -10f));
		/*
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(1);
		BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = BodyType.DYNAMIC;
		circleBodyDef.position.set(0, 60);
		Body circle = getWorld().createBody(circleBodyDef);
		FixtureDef circleFixtureDef = new FixtureDef();
		circleFixtureDef.friction = 0.4f;
		circleFixtureDef.restitution =1.0f;
		circleFixtureDef.shape = circleShape;
		circle.createFixture(circleFixtureDef);
		//circle.applyForce(new Vec2(0, -10000), new Vec2(0, 60));
		//circle.createFixture(circleShape, 5.0f);
		PolygonShape line = new PolygonShape();
		line.setAsBox(100, 0.0f);
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position.set(0, -10);
		//polygonBodyDef.angle = (float) (Math.PI / 4);
		Body body = getWorld().createBody(def);
		body.createFixture(line, 0.0f);
*/

/*
		//Ground Body
		//Step 1
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0.0f, -10.0f);

		//Step 2
		Body groundBody = getWorld().createBody(groundBodyDef);

		//Step 3
		//PolygonShape groundBox = new PolygonShape();
		//groundBox.setAsBox(50.0f, 10.0f);
		EdgeShape groundBox = new EdgeShape();
		groundBox.set(new Vec2(-10.0f, 0.0f), new Vec2(10.0f, 0.0f));

		//Step 4
		groundBody.createFixture(groundBox, 0.0f);


		//Dynamic Body
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(0.0f, 20.0f);
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.angle = (float) (0.25f * Math.PI);
		Body body = getWorld().createBody(bodyDef);
		PolygonShape dynamicBox = new PolygonShape();
		//dynamicBox.setAsBox(10, 1);
		dynamicBox.setAsBox(10, 1, new Vec2(20, 2), 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.friction = 0.3f;
		fixtureDef.density = 1.0f;
		fixtureDef.restitution = 0.8f;
		MassData massData = new MassData();

		body.createFixture(fixtureDef);

		getWorld().setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				System.out.println("begin");
			}

			@Override
			public void endContact(Contact contact) {
				System.out.println("end");
			}

			@Override
			public void preSolve(Contact contact, Manifold manifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse contactImpulse) {

			}
		});
*/

		Body ground = null;
		{
			BodyDef bd = new BodyDef();
			ground = getWorld().createBody(bd);

			EdgeShape shape = new EdgeShape();
			shape.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
			ground.createFixture(shape, 0.0f);
		}

		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.gravityScale = 0;
		int a = 5;
		def.position.set(-10, 10);
		//def.angle = (float) (Math.PI / 2);
		Body body = getWorld().createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.set(new Vec2[]{new Vec2(-5, -5), new Vec2(-5, 5), new Vec2(5, -5)}, 3);
		body.createFixture(shape, 1);

		/*{
			CircleShape shape = new CircleShape();
			shape.m_radius = 0.5f;

			BodyDef bd = new BodyDef();
			bd.type = BodyType.DYNAMIC;

			RevoluteJointDef rjd = new RevoluteJointDef();

			bd.position.set(-10f, 20.0f);
			Body body = getWorld().createBody(bd);
			body.createFixture(shape, 5.0f);

			float w = 100.0f;
			body.setAngularVelocity(w);
			body.setLinearVelocity(new Vec2(-8.0f * w, 0.0f));

			rjd.initialize(ground, body, new Vec2(-10.0f, 12.0f));
			rjd.motorSpeed = -1.0f * MathUtils.PI;
			rjd.maxMotorTorque = 10000.0f;
			rjd.enableMotor = false;
			rjd.lowerAngle = -0.25f * MathUtils.PI;
			rjd.upperAngle = 0.5f * MathUtils.PI;
			rjd.enableLimit = true;
			rjd.collideConnected = true;

			RevoluteJoint m_joint = (RevoluteJoint) getWorld().createJoint(rjd);
		}*/

		{
			CircleShape circle_shape = new CircleShape();
			circle_shape.m_radius = 3.0f;

			BodyDef circle_bd = new BodyDef();
			circle_bd.type = BodyType.DYNAMIC;
			circle_bd.position.set(5.0f, 50.0f);

			FixtureDef fd = new FixtureDef();
			fd.density = 5.0f;
			fd.filter.maskBits = 1;
			fd.shape = circle_shape;

			Body ball = m_world.createBody(circle_bd);
			ball.createFixture(fd);

			PolygonShape polygon_shape = new PolygonShape();
			polygon_shape.setAsBox(5.0f, 1.0f, new Vec2(-5.0f, 0.0f), 0.0f);

			BodyDef polygon_bd = new BodyDef();
			polygon_bd.position.set(10.0f, 5.0f);
			polygon_bd.type = BodyType.DYNAMIC;
			polygon_bd.gravityScale = 100.0f;
			//polygon_bd.bullet = true;
			Body polygon_body = m_world.createBody(polygon_bd);
			polygon_body.createFixture(polygon_shape, 2.0f);

			RevoluteJointDef rjd = new RevoluteJointDef();
			rjd.initialize(ground, polygon_body, new Vec2(10.0f, 5.0f));
			rjd.lowerAngle = -0.25f * MathUtils.PI;
			rjd.upperAngle = -rjd.lowerAngle;
			rjd.enableLimit = true;
			m_world.createJoint(rjd);
		}
/*
		// Tests mass computation of a small object far from the origin
		{
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DYNAMIC;
			Body body = m_world.createBody(bodyDef);

			PolygonShape polyShape = new PolygonShape();
			Vec2 verts[] = new Vec2[3];
			verts[0] = new Vec2(17.63f, 36.31f);
			verts[1] = new Vec2(17.52f, 36.69f);
			verts[2] = new Vec2(17.19f, 36.36f);
			polyShape.set(verts, 3);

			FixtureDef polyFixtureDef = new FixtureDef();
			polyFixtureDef.shape = polyShape;
			polyFixtureDef.density = 1;

			body.createFixture(polyFixtureDef); // assertion hits inside here
		}*/
	}


	@Override
	public String getTestName() {
		return "Couple of Things";
	}


}