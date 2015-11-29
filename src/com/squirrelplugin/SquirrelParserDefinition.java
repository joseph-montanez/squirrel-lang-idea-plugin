package com.squirrelplugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.squirrelplugin.parser.SquirrelParser;
import com.squirrelplugin.psi.SquirrelFile;
import com.squirrelplugin.psi.SquirrelTokenType;
import com.squirrelplugin.psi.SquirrelTypes;
import org.jetbrains.annotations.NotNull;

import static com.squirrelplugin.psi.SquirrelTypes.*;

public class SquirrelParserDefinition implements ParserDefinition {

    public static final IElementType WS = new SquirrelTokenType("WS");
    public static final IElementType NLS = new SquirrelTokenType("NL");
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE, WS, NLS);

    public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT, BLOCK_COMMENT);

    public static final TokenSet NUMBERS = TokenSet.create(INT, FLOAT); // todo: HEX, OCT,
    public static final TokenSet STRING_LITERALS = TokenSet.create(STRING); // todo: strings with @


    public static final TokenSet KEYWORDS = TokenSet.create(CONST, FUNCTION);
    public static final TokenSet OPERATORS = TokenSet.create(
            EQ, ASSIGN, NOT_EQ, NOT, PLUS_PLUS, PLUS_ASSIGN, PLUS, MINUS_MINUS, MINUS_ASSIGN, MINUS, COND_OR, BIT_OR_ASSIGN, BIT_OR,
            BIT_CLEAR_ASSIGN, BIT_CLEAR, COND_AND, BIT_AND_ASSIGN, BIT_AND, SHIFT_LEFT_ASSIGN, SHIFT_LEFT, SEND_CHANNEL, LESS_OR_EQUAL,
            LESS, BIT_XOR_ASSIGN, BIT_XOR, MUL_ASSIGN, MUL, QUOTIENT_ASSIGN, QUOTIENT, REMAINDER_ASSIGN, REMAINDER, SHIFT_RIGHT_ASSIGN,
            SHIFT_RIGHT, GREATER_OR_EQUAL, GREATER);


    public static final IFileElementType FILE = new IFileElementType(Language.<SquirrelLanguage>findInstance(SquirrelLanguage.class));

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new SquirrelLexerAdapter();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new SquirrelParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new SquirrelFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return SquirrelTypes.Factory.createElement(node);
    }
}