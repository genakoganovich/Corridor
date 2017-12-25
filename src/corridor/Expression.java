package corridor;

import java.util.Vector;

public abstract class Expression {
    abstract double evaluate();
}


class Number extends Expression {
    final double value;
    Number(double value) {
        this.value = value;
    }
    Number(Vector<String> vector, int index) {
        value = Util.get(vector, index);
    }
    @Override
    double evaluate() {
        return value;
    }
}

abstract class BinaryOperation extends Expression {
    Expression op1;
    Expression op2;
    BinaryOperation(Expression op1, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
    }
}
class Sum extends BinaryOperation {
    Sum(Expression op1, Expression op2) {
        super(op1, op2);
    }
    @Override
    double evaluate() {
        return op1.evaluate() + op2.evaluate();
    }
}
class Difference extends BinaryOperation {
    Difference(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    double evaluate() {
        return op1.evaluate() - op2.evaluate();
    }
}
class Product extends BinaryOperation {
    Product(Expression op1, Expression op2) {
        super(op1, op2);
    }
    @Override
    double evaluate() {
        return op1.evaluate() * op2.evaluate();
    }
}
class Quotient extends BinaryOperation {
    Quotient(Expression op1, Expression op2) {
        super(op1, op2);
    }
    @Override
    double evaluate() {
        return op1.evaluate() / op2.evaluate();
    }
}