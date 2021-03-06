package Examples;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.Self;

public class opg11b {

	// The main method will create some nodes, tuple spaces and agents
	public static void main(String[] argv) {

		// We create here a single node that we call "fridge"
		Node Appartment = new Node("fridge", new TupleSpace());
		
		// We create Alice and Bob as Producer/Consumer agents
		// The constructor of agents takes the name of the agent as argument
		Agent Alice = new Alice("Alice");
		Agent Bob = new Bob("Bob");
		Agent Dave = new Bob("Dave");
//		Agent Charlie = new Charlie("Charlie");
		// We add both agents to the fridge node
		Appartment.addAgent(Alice);
		Appartment.addAgent(Bob);
		Appartment.addAgent(Dave);
//		Appartment.addAgent(Charlie);
		// We start the node
		Appartment.start();
		
	}

	public static class Alice extends Agent {

		// This constructor records the name of the agent
		public Alice(String name) {
			super(name);
		}

		// This is the function invoked when the agent starts running in a node
		@Override
		protected void doRun() {
			try {
				System.out.println(name + " adding items to the grocery list...");
				System.out.println(name + " adding one bottle(s) of milk");
				// put takes to arguments: the tuple to be put and the target node
				// tuples are objects of class Tuple, whose constructor takes a list of values
				// Self.SELF is the node where the agent resides
				put(new Tuple("milk",1,"fd"), Self.SELF);
				System.out.println(name + " adding one piece of soap");
				put(new Tuple("soap",2,"ds"), Self.SELF);
				System.out.println(name + " adding three piecess of butter");
				put(new Tuple("butter",3,"fd"), Self.SELF);
				System.out.println(name + " go!");
				put(new Tuple("shop!"), Self.SELF);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static class Bob extends Agent {

		public Bob(String name) {
			super(name);
		}

		@Override
		protected void doRun() {
			// Note how templates are created in jRESP
			// In place of binding variables we need to use so-called formal fields
			// We will see later how these fields can be saved into variables
			// The formal field constructor needs a class as parameter
			Template what = new Template(
					new FormalTemplateField(String.class),
					new FormalTemplateField(Integer.class),
					new FormalTemplateField(String.class)
					);
			// Value fields in templates are specified as actual template fields
			Template go = new Template(
					new ActualTemplateField("shop!")
					);
			// The tuple will be used to capture the result of a get operation
			Tuple t;
			try {
				query(go,Self.SELF);
				while (true) {
					// The get operation returns a tuple, that we save into t
					t = query(what, Self.SELF);
					if (t.getElementAt(String.class, 2) == "fd") {
						t = get(what, Self.SELF);
						// Note how the fields of the tuple t are accessed
						// The type of the field needs to be specified
						System.out.println(name + " shopping " + t.getElementAt(Integer.class,1) + " units of " + t.getElementAt(String.class, 0) + "...");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class Dave extends Agent {

		public Dave(String name) {
			super(name);
		}

		@Override
		protected void doRun() {
			// Note how templates are created in jRESP
			// In place of binding variables we need to use so-called formal fields
			// We will see later how these fields can be saved into variables
			// The formal field constructor needs a class as parameter
			Template what = new Template(
					new FormalTemplateField(String.class),
					new FormalTemplateField(Integer.class),
					new FormalTemplateField(String.class)
					);
			// Value fields in templates are specified as actual template fields
			Template go = new Template(
					new ActualTemplateField("shop!")
					);
			// The tuple will be used to capture the result of a get operation
			Tuple t;
			try {
				query(go,Self.SELF);
				while (true) {
					// The get operation returns a tuple, that we save into t
					t = query(what, Self.SELF);
					if (t.getElementAt(String.class, 2) == "ds") {
						t = get(what, Self.SELF);
						// Note how the fields of the tuple t are accessed
						// The type of the field needs to be specified
						System.out.println(name + " shopping " + t.getElementAt(Integer.class,1) + " units of " + t.getElementAt(String.class, 0) + "...");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
//	public static class Charlie extends Agent {
//
//		public Charlie(String who) {
//			super(who);
//		}
//
//		@Override
//		protected void doRun() {
//			Template what = new Template(
//					new FormalTemplateField(String.class),
//					new FormalTemplateField(Integer.class),
//					new FormalTemplateField(String.class)
//					);
//			Template go = new Template(
//					new ActualTemplateField("shop!")
//					);
//			Tuple t;
//			try {
//				if (queryp(go) != null){
//					while (true) {
//						t = get(what, Self.SELF);
//						System.out.println(name + " shopping " + t.getElementAt(Integer.class, 1) + " units of " + t.getElementAt(String.class, 0) + "...");
//					}
//				} else {
//					System.out.println(name + " relaxing...");
//				}
//					
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}

}