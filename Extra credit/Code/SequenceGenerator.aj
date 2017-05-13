import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public aspect SequenceGenerator {

	int depth;
	ArrayList<Message> messages = new ArrayList<Message>();
	Stack<String> classStack = new Stack<String>();
	private static final String OUTPUT_FILENAME = "plantumlseq.txt";

	pointcut instanceMethods() : !within(SequenceGenerator) && execution(* *.*(..)) && 
    !cflow(execution(*.new(..)));

	before(): instanceMethods() {
		print("Before", thisJoinPoint);
		thisJoinPoint.getArgs();
		Object targt = thisJoinPoint.getTarget();
		Object cur = thisJoinPoint.getThis();
		String name = thisJoinPoint.getSourceLocation().getFileName();
		String sig = thisEnclosingJoinPointStaticPart.getSourceLocation().toString();

		if (cur != null && targt != null) {
			Message msg = new Message();
			msg.source = classStack.peek();
			msg.dest = targt.getClass().getName();
			classStack.push(msg.dest);
			msg.arrow = " -> ";
			//msg.methodSignature = thisJoinPoint.getSignature().getName();
			String meth = thisJoinPoint.getSignature().toString();
			msg.methodSignature = meth.split(" ")[1].split("\\.")[1] + " : " + meth.split(" ")[0];
			messages.add(msg);
			Message act = new Message();
			act.source = "activate " + msg.dest;
			messages.add(act);
		} else {
			classStack.push("Main");
		}

		depth++;
		print("CallDepth", depth);

	}

	after(): instanceMethods() {
		print("After", thisJoinPoint);
		depth--;
		Object cur = thisJoinPoint.getThis();
		Object targt = thisJoinPoint.getTarget();
		if (cur == null && thisJoinPoint.getSignature().getName().contains("main")) {
			generateSeqDiagramTxtFile();
		}

		String name = thisJoinPoint.getSignature().getName();

		if (cur != null && targt != null) {
			Message msg = new Message();
			msg.source = classStack.pop();
			msg.dest = classStack.peek();
			msg.arrow = " --> ";
			msg.methodSignature = thisJoinPoint.getSignature().getName();
			if(!msg.source.equalsIgnoreCase(msg.dest)){
				messages.add(msg);
			}
			Message deact = new Message();
			deact.source = "deactivate " + msg.source;
			messages.add(deact);
		}
		// print("CallDepth", depth);

	}

	private void generateSeqDiagramTxtFile() {
		try {
			PrintWriter writer = new PrintWriter(OUTPUT_FILENAME, "UTF-8");
			writer.println("@startuml");
			writer.println("skinparam classAttributeIconSize 0");

			for (Message msg : messages) {
				if(msg.dest != null){
					writer.println(msg.source + msg.arrow + msg.dest + " : " + msg.methodSignature);
				}else{
					writer.println(msg.source);
				}
			}

			writer.println("@enduml");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(String prefix, Object message) {
		for (int i = 0; i < depth; i++) {
			System.out.print(" ");
		}
		System.out.println(prefix + ": " + message);
	}

}
