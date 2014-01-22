package phaseA;
import providedCode.*;


import java.util.EmptyStackException;

public class GArrayStack<E> implements GStack {
	private E[] stackArray;
	//index of the top of the stack
	private int top;
	private final int INITIAL_SIZE = 10;
	private final int RESIZE_FACTOR = 2;
	
	public GArrayStack(){
		stackArray = (E[]) new Object[INITIAL_SIZE];
		top = 0;
	}
	
	//returns whether or not the stack is empty
	@Override
	public boolean isEmpty() {
		return top == 0;
	}
	
	//takes in a value and pushes it to the top of the stack.
	//if no room, doubles the capacity of the stack.
	@Override
	public void push(Object o) {
		if (top == stackArray.length)
			resize(stackArray.length * RESIZE_FACTOR);
		stackArray[top] = (E) o;
		top++;
	}

	//decrements the stack index and returns the popped value
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		top--;
		return stackArray[top];
	}

	//if nonempty, returns the value at the top of the stack
	@Override
	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return stackArray[top - 1];
	}
	
	//takes in a size and copies the current stack elements to
	//a stack of that size
	private void resize(int newSize) {
		E[] newArray = (E[]) new Object[newSize];
		for (int i = 0; i < stackArray.length; i++) {
			newArray[i] = stackArray[i];
		}
		stackArray = newArray;
	}
	
}
