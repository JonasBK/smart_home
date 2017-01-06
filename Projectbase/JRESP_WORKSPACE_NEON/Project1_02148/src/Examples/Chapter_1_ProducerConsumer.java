package Examples;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.Self;

public class Chapter_1_ProducerConsumer {

	public static void main(String[] argv) {

		// We create here a single node that we call "fridge"
		Node Appartment = new Node("fridge", new TupleSpace());

		// We create Alice and Bob as Producer/Consumer agents
		// The constructor of agents takes the name of the agent as argument
		Agent Alice = new Producer("Alice");
		Agent Bob = new Consumer("Bob");
		// We add both agents to the fridge node
		Appartment.addAgent(Alice);
		Appartment.addAgent(Bob);
		// We start the node
		Appartment.start();

	}

	public static class Producer extends Agent {

		// This constructor records the name of the agent
		public Producer(String name) {
			super(name);
		}

		// This is the function invoked when the agent starts running in a node
		@Override
		protected void doRun() {
			try {
				System.out.println(name + " adding items to the grocery list...");
				System.out.println(name + " adding milk(1)");
				// put takes to arguments: the tuple to be put and the target node
				// tuples are objects of class Tuple, whose constructor takes a list of values
				// Self.SELF is the node where the agent resides
				put(new Tuple("milk",1), Self.SELF);
				System.out.println(name + " adding soap(2)");
				put(new Tuple("soap",2), Self.SELF);
				System.out.println(name + " adding butter(3)");
				put(new Tuple("butter",3), Self.SELF);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static class Consumer extends Agent {

		public Consumer(String name) {
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
					new FormalTemplateField(Integer.class)
					);
			// The tuple is necessary to capture the result of a get operation
			Tuple t;
			try {
				while (true) {
					// The get operation returns a tuple, that we save into t
					t = get(what, Self.SELF);
					// Note how the fields of the tuple t are accessed
					// The extracted field needs often to be casted to the right class
					System.out.println(name + " shopping " + t.getElementAt(Integer.class,1) + " units of " + t.getElementAt(String.class, 0) + "...");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}