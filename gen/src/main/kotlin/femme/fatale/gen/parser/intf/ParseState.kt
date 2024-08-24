package femme.fatale.gen.parser.intf

import femme.fatale.gen.lexer.intf.TokenType
import femme.fatale.gen.lexer.tokens.*
import femme.fatale.gen.parser.Parser
import femme.fatale.gen.parser.nodes.*
import java.util.*

internal class ParseState {
    fun handle(context: Parser) {
        val token = context.current
        when (token.tokenType) {
            TokenType.Literal ->
                addOperand(LiteralNode(token as LiteralToken))

            TokenType.Repetition ->
                addOperator(RepetitionNode(token as RepetitionToken))

            TokenType.ParenthesisLeft ->
                context.toState(ParseState())

            TokenType.ParenthesisRight -> {
                addOperator(ParenthesisNode(token as ParenthesisRightToken))
                context.endState()
            }

            TokenType.Alternation ->
                addOperator(AlternationNode(token as AlternationToken))

            TokenType.Range ->
                addOperator(RangeNode(token as RangeToken))

            TokenType.BracketLeft ->
                context.toState(ParseState())

            TokenType.BracketRight -> {
                addOperator(BracketNode(token as BracketRightToken))
                context.endState()
            }

            TokenType.Any ->
                addOperand(NodeBuilder.buildAnyNode())

            TokenType.Not ->
                addOperator(NotNode(token as NotToken))

            TokenType.Numeric ->
                addOperand(NodeBuilder.buildNumericNode())

            TokenType.Word ->
                addOperand(NodeBuilder.buildWordNode())

            TokenType.Whitespace ->
                addOperand(NodeBuilder.buildWhitespaceNode())

            TokenType.NonNumeric ->
                addOperand(NodeBuilder.buildNonNumericNode())

            TokenType.NonWord ->
                addOperand(NodeBuilder.buildNonWordNode())

            TokenType.NonWhitespace ->
                addOperand(NodeBuilder.buildNonWhitespaceNode())

            TokenType.Concatenation -> {}
        }
    }

    private val operators: Stack<INode> = Stack()
    private val operands: Stack<INode> = Stack()

    private fun addOperator(operatorNode: INode) {
        while (operators.isNotEmpty() && operators.peek().precedence > operatorNode.precedence) {
            processOperator(operators.pop())
        }
        operators.push(operatorNode)
    }

    fun addOperand(operandNode: INode) {
        if (!isBalanced())
            addOperator(ConcatenationNode(ConcatenationToken()))
        operands.push(operandNode)
    }

    private fun processOperator(operatorNode: INode) {
        when (operatorNode.nodeType) {
            NodeType.UnaryOperator -> {
                operatorNode.childNodes.add(0, operands.pop())
                operands.push(operatorNode)
            }

            NodeType.BinaryOperator -> {
                val lastOperand = operands.pop()
                if (lastOperand.token.tokenType == TokenType.Concatenation && operatorNode.token.tokenType == TokenType.Concatenation) {
                    for (childNode in lastOperand.childNodes) {
                        operatorNode.childNodes.add(childNode)
                    }
                } else {
                    operatorNode.childNodes.add(lastOperand)
                }
                operatorNode.childNodes.add(0, operands.pop())
                operands.push(operatorNode)
            }

            NodeType.Operand -> {}
        }
    }

    private fun isBalanced(): Boolean {
        return operators.filter { it.nodeType == NodeType.BinaryOperator }.size == operands.size
    }

    fun close(): INode {
        while (operators.isNotEmpty()) {
            processOperator(operators.pop())
        }
        return operands.pop()
    }
}
