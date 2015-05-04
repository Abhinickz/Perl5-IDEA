// This is a generated file. Not intended for manual editing.
package com.perl5.lang.perl.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.perl5.lang.perl.lexer.PerlElementTypes.*;
import static com.perl5.lang.perl.parser.PerlParserUitl.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class PerlParser implements PsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ARRAY) {
      r = array(b, 0);
    }
    else if (t == BLOCK) {
      r = block(b, 0);
    }
    else if (t == BLOCK_BLOCK) {
      r = block_block(b, 0);
    }
    else if (t == BLOCK_CONDITIONAL) {
      r = block_conditional(b, 0);
    }
    else if (t == CALEE) {
      r = calee(b, 0);
    }
    else if (t == CALL_ARGUMENTS) {
      r = call_arguments(b, 0);
    }
    else if (t == CALL_LEFTWARD) {
      r = call_leftward(b, 0);
    }
    else if (t == CALL_RIGHTWARD) {
      r = call_rightward(b, 0);
    }
    else if (t == CODE_LINE) {
      r = code_line(b, 0);
    }
    else if (t == EVAL_EXPR) {
      r = eval_expr(b, 0);
    }
    else if (t == EXPR) {
      r = expr(b, 0, -1);
    }
    else if (t == FILE_ITEM) {
      r = file_item(b, 0);
    }
    else if (t == FOR_BLOCK) {
      r = for_block(b, 0);
    }
    else if (t == FOR_BLOCK_ARGUMENTS) {
      r = for_block_arguments(b, 0);
    }
    else if (t == GIVEN_BLOCK) {
      r = given_block(b, 0);
    }
    else if (t == GREP_EXPR) {
      r = grep_expr(b, 0);
    }
    else if (t == HASH) {
      r = hash(b, 0);
    }
    else if (t == IF_BLOCK) {
      r = if_block(b, 0);
    }
    else if (t == IF_BLOCK_ELSE) {
      r = if_block_else(b, 0);
    }
    else if (t == IF_BLOCK_ELSIF) {
      r = if_block_elsif(b, 0);
    }
    else if (t == IF_POSTFIX) {
      r = if_postfix(b, 0);
    }
    else if (t == KEYS_ARGS) {
      r = keys_args(b, 0);
    }
    else if (t == KEYS_EXPR) {
      r = keys_expr(b, 0);
    }
    else if (t == LAST_EXPR) {
      r = last_expr(b, 0);
    }
    else if (t == LIST_EXPR) {
      r = list_expr(b, 0);
    }
    else if (t == LOCAL_DEFINITION) {
      r = local_definition(b, 0);
    }
    else if (t == LOCAL_VARIABLE_DEFINITION_ARGUMENTS) {
      r = local_variable_definition_arguments(b, 0);
    }
    else if (t == MY_DEFINITION) {
      r = my_definition(b, 0);
    }
    else if (t == OBJECT_METHOD) {
      r = object_method(b, 0);
    }
    else if (t == OBJECT_METHOD_OBJECT) {
      r = object_method_object(b, 0);
    }
    else if (t == OP_10_EXPR) {
      r = op_10_expr(b, 0);
    }
    else if (t == OP_11_EXPR) {
      r = expr(b, 0, 12);
    }
    else if (t == OP_12_EXPR) {
      r = expr(b, 0, 11);
    }
    else if (t == OP_13_EXPR) {
      r = expr(b, 0, 10);
    }
    else if (t == OP_14_EXPR) {
      r = expr(b, 0, 9);
    }
    else if (t == OP_15_EXPR) {
      r = expr(b, 0, 8);
    }
    else if (t == OP_16_EXPR) {
      r = expr(b, 0, 7);
    }
    else if (t == OP_17_EXPR) {
      r = expr(b, 0, 6);
    }
    else if (t == OP_18_EXPR) {
      r = expr(b, 0, 5);
    }
    else if (t == OP_19_EXPR) {
      r = expr(b, 0, 4);
    }
    else if (t == OP_1_EXPR) {
      r = op_1_expr(b, 0);
    }
    else if (t == OP_20_EXPR) {
      r = expr(b, 0, 3);
    }
    else if (t == OP_21_EXPR) {
      r = op_21_expr(b, 0);
    }
    else if (t == OP_22_EXPR) {
      r = op_22_expr(b, 0);
    }
    else if (t == OP_23_EXPR) {
      r = expr(b, 0, 0);
    }
    else if (t == OP_24_EXPR) {
      r = expr(b, 0, -1);
    }
    else if (t == OP_2_EXPR) {
      r = expr(b, 0, 21);
    }
    else if (t == OP_3_PREF_EXPR) {
      r = op_3_pref_expr(b, 0);
    }
    else if (t == OP_3_SUFF_EXPR) {
      r = expr(b, 0, 20);
    }
    else if (t == OP_4_EXPR) {
      r = expr(b, 0, 19);
    }
    else if (t == OP_5_EXPR) {
      r = op_5_expr(b, 0);
    }
    else if (t == OP_6_EXPR) {
      r = expr(b, 0, 17);
    }
    else if (t == OP_7_EXPR) {
      r = expr(b, 0, 16);
    }
    else if (t == OP_8_EXPR) {
      r = expr(b, 0, 15);
    }
    else if (t == OP_9_EXPR) {
      r = expr(b, 0, 14);
    }
    else if (t == OUR_DEFINITION) {
      r = our_definition(b, 0);
    }
    else if (t == PACKAGE_DEFINITION) {
      r = package_definition(b, 0);
    }
    else if (t == PACKAGE_METHOD) {
      r = package_method(b, 0);
    }
    else if (t == PACKAGE_NO) {
      r = package_no(b, 0);
    }
    else if (t == PACKAGE_REQUIRE) {
      r = package_require(b, 0);
    }
    else if (t == PACKAGE_USE) {
      r = package_use(b, 0);
    }
    else if (t == PACKAGE_USE_ARGUMENTS) {
      r = package_use_arguments(b, 0);
    }
    else if (t == PERL_REGEX) {
      r = perl_regex(b, 0);
    }
    else if (t == PERL_REGEX_MODIFIERS) {
      r = perl_regex_modifiers(b, 0);
    }
    else if (t == PERL_VERSION) {
      r = perl_version(b, 0);
    }
    else if (t == QW_EXPR) {
      r = qw_expr(b, 0);
    }
    else if (t == REGEX_EXPR) {
      r = regex_expr(b, 0);
    }
    else if (t == RETURN_EXPR) {
      r = return_expr(b, 0);
    }
    else if (t == SCALAR_EXPR) {
      r = scalar_expr(b, 0);
    }
    else if (t == SCALAR_NESTED_ELEMENT) {
      r = scalar_nested_element(b, 0);
    }
    else if (t == SHIFT_EXPR) {
      r = shift_expr(b, 0);
    }
    else if (t == SORT_EXPR) {
      r = sort_expr(b, 0);
    }
    else if (t == SORT_OP_ARGS) {
      r = sort_op_args(b, 0);
    }
    else if (t == SPLIT_ARGS) {
      r = split_args(b, 0);
    }
    else if (t == SPLIT_EXPR) {
      r = split_expr(b, 0);
    }
    else if (t == SUB_BLOCK_ANON) {
      r = sub_block_anon(b, 0);
    }
    else if (t == SUB_BLOCK_NAMED) {
      r = sub_block_named(b, 0);
    }
    else if (t == TERM) {
      r = term(b, 0);
    }
    else if (t == TR_MODIFIERS) {
      r = tr_modifiers(b, 0);
    }
    else if (t == TR_REGEX) {
      r = tr_regex(b, 0);
    }
    else if (t == TR_REPLACEMENTLIST) {
      r = tr_replacementlist(b, 0);
    }
    else if (t == TR_SEARCHLIST) {
      r = tr_searchlist(b, 0);
    }
    else if (t == VARIABLE_DEFINITION) {
      r = variable_definition(b, 0);
    }
    else if (t == VARIABLE_DEFINITION_ARGUMENTS) {
      r = variable_definition_arguments(b, 0);
    }
    else if (t == WHILE_BLOCK) {
      r = while_block(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return perlFile(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(EVAL_EXPR, EXPR, GREP_EXPR, KEYS_EXPR,
      LAST_EXPR, LIST_EXPR, OP_10_EXPR, OP_11_EXPR,
      OP_12_EXPR, OP_13_EXPR, OP_14_EXPR, OP_15_EXPR,
      OP_16_EXPR, OP_17_EXPR, OP_18_EXPR, OP_19_EXPR,
      OP_1_EXPR, OP_20_EXPR, OP_21_EXPR, OP_22_EXPR,
      OP_23_EXPR, OP_24_EXPR, OP_2_EXPR, OP_3_PREF_EXPR,
      OP_3_SUFF_EXPR, OP_4_EXPR, OP_5_EXPR, OP_6_EXPR,
      OP_7_EXPR, OP_8_EXPR, OP_9_EXPR, QW_EXPR,
      REGEX_EXPR, RETURN_EXPR, SCALAR_EXPR, SHIFT_EXPR,
      SORT_EXPR, SPLIT_EXPR),
  };

  /* ********************************************************** */
  // array_safe '[' expr ']'              // array slice
  //     | array_safe '{' expr '}'              // hash slice
  //     | array_safe
  //     | '(' ')'
  public static boolean array(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<array>");
    r = array_0(b, l + 1);
    if (!r) r = array_1(b, l + 1);
    if (!r) r = array_safe(b, l + 1);
    if (!r) r = array_3(b, l + 1);
    exit_section_(b, l, m, ARRAY, r, false, null);
    return r;
  }

  // array_safe '[' expr ']'
  private static boolean array_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_safe(b, l + 1);
    r = r && consumeToken(b, PERL_LBRACK);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  // array_safe '{' expr '}'
  private static boolean array_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_safe(b, l + 1);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' ')'
  private static boolean array_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '->'? '[' expr ']'
  static boolean array_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_element_0(b, l + 1);
    r = r && consumeToken(b, PERL_LBRACK);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  // '->'?
  private static boolean array_element_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_element_0")) return false;
    consumeToken(b, "->");
    return true;
  }

  /* ********************************************************** */
  // PERL_SIGIL_ARRAY '{' expr '}'   // scalar dereference
  //     | PERL_SIGIL_ARRAY scalar_expr       // scalar dereference
  //     | PERL_ARRAY
  static boolean array_safe(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_safe")) return false;
    if (!nextTokenIs(b, "", PERL_ARRAY, PERL_SIGIL_ARRAY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_safe_0(b, l + 1);
    if (!r) r = array_safe_1(b, l + 1);
    if (!r) r = consumeToken(b, PERL_ARRAY);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SIGIL_ARRAY '{' expr '}'
  private static boolean array_safe_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_safe_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_ARRAY);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SIGIL_ARRAY scalar_expr
  private static boolean array_safe_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_safe_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_ARRAY);
    r = r && scalar_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // label ? '{' (file_item + | expr )? '}'
  public static boolean block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block")) return false;
    if (!nextTokenIs(b, "<block>", PERL_LBRACE, PERL_BAREWORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<block>");
    r = block_0(b, l + 1);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && block_2(b, l + 1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, l, m, BLOCK, r, false, null);
    return r;
  }

  // label ?
  private static boolean block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0")) return false;
    label(b, l + 1);
    return true;
  }

  // (file_item + | expr )?
  private static boolean block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_2")) return false;
    block_2_0(b, l + 1);
    return true;
  }

  // file_item + | expr
  private static boolean block_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block_2_0_0(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // file_item +
  private static boolean block_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = file_item(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!file_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "block_2_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // block ('continue' block ) ?
  public static boolean block_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_block")) return false;
    if (!nextTokenIs(b, "<block block>", PERL_LBRACE, PERL_BAREWORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<block block>");
    r = block(b, l + 1);
    r = r && block_block_1(b, l + 1);
    exit_section_(b, l, m, BLOCK_BLOCK, r, false, null);
    return r;
  }

  // ('continue' block ) ?
  private static boolean block_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_block_1")) return false;
    block_block_1_0(b, l + 1);
    return true;
  }

  // 'continue' block
  private static boolean block_block_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_block_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "continue");
    r = r && block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' expr ')' block
  public static boolean block_conditional(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_conditional")) return false;
    if (!nextTokenIs(b, PERL_LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RPAREN);
    r = r && block(b, l + 1);
    exit_section_(b, m, BLOCK_CONDITIONAL, r);
    return r;
  }

  /* ********************************************************** */
  // eval_expr
  //     | sub_block_named
  //     | for_block
  //     | if_block
  //     | while_block
  //     | given_block
  //     | block_block
  //     | PERL_POD
  static boolean block_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = eval_expr(b, l + 1);
    if (!r) r = sub_block_named(b, l + 1);
    if (!r) r = for_block(b, l + 1);
    if (!r) r = if_block(b, l + 1);
    if (!r) r = while_block(b, l + 1);
    if (!r) r = given_block(b, l + 1);
    if (!r) r = block_block(b, l + 1);
    if (!r) r = consumeToken(b, PERL_POD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // package_method
  //     | perl_method_parent
  //     | object_method
  //     | perl_package_function
  //     | (perl_function perl_package)
  //     | perl_function
  //     | object_method_object
  //     | referencable_method
  public static boolean calee(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "calee")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<calee>");
    r = package_method(b, l + 1);
    if (!r) r = perl_method_parent(b, l + 1);
    if (!r) r = object_method(b, l + 1);
    if (!r) r = perl_package_function(b, l + 1);
    if (!r) r = calee_4(b, l + 1);
    if (!r) r = perl_function(b, l + 1);
    if (!r) r = object_method_object(b, l + 1);
    if (!r) r = referencable_method(b, l + 1);
    exit_section_(b, l, m, CALEE, r, false, null);
    return r;
  }

  // perl_function perl_package
  private static boolean calee_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "calee_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = perl_function(b, l + 1);
    r = r && perl_package(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<parseCallArguments>> expr | expr
  public static boolean call_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<call arguments>");
    r = call_arguments_0(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    exit_section_(b, l, m, CALL_ARGUMENTS, r, false, null);
    return r;
  }

  // <<parseCallArguments>> expr
  private static boolean call_arguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_arguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseCallArguments(b, l + 1);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // calee '(' call_arguments ? ')'
  //     | scalar_expr '(' call_arguments ? ')'
  //     | scalar_expr '->' '(' call_arguments ? ')'
  public static boolean call_leftward(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<call leftward>");
    r = call_leftward_0(b, l + 1);
    if (!r) r = call_leftward_1(b, l + 1);
    if (!r) r = call_leftward_2(b, l + 1);
    exit_section_(b, l, m, CALL_LEFTWARD, r, false, null);
    return r;
  }

  // calee '(' call_arguments ? ')'
  private static boolean call_leftward_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = calee(b, l + 1);
    r = r && consumeToken(b, PERL_LPAREN);
    r = r && call_leftward_0_2(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // call_arguments ?
  private static boolean call_leftward_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward_0_2")) return false;
    call_arguments(b, l + 1);
    return true;
  }

  // scalar_expr '(' call_arguments ? ')'
  private static boolean call_leftward_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scalar_expr(b, l + 1);
    r = r && consumeToken(b, PERL_LPAREN);
    r = r && call_leftward_1_2(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // call_arguments ?
  private static boolean call_leftward_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward_1_2")) return false;
    call_arguments(b, l + 1);
    return true;
  }

  // scalar_expr '->' '(' call_arguments ? ')'
  private static boolean call_leftward_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scalar_expr(b, l + 1);
    r = r && consumeToken(b, "->");
    r = r && consumeToken(b, PERL_LPAREN);
    r = r && call_leftward_2_3(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // call_arguments ?
  private static boolean call_leftward_2_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_leftward_2_3")) return false;
    call_arguments(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // calee !"(" call_arguments ?
  public static boolean call_rightward(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_rightward")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<call rightward>");
    r = calee(b, l + 1);
    r = r && call_rightward_1(b, l + 1);
    r = r && call_rightward_2(b, l + 1);
    exit_section_(b, l, m, CALL_RIGHTWARD, r, false, null);
    return r;
  }

  // !"("
  private static boolean call_rightward_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_rightward_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !consumeToken(b, PERL_LPAREN);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // call_arguments ?
  private static boolean call_rightward_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call_rightward_2")) return false;
    call_arguments(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // expr
  public static boolean code_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_line")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<code line>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, CODE_LINE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('eval'|'do' ) (block | expr)
  public static boolean eval_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eval_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<eval expr>");
    r = eval_expr_0(b, l + 1);
    r = r && eval_expr_1(b, l + 1);
    exit_section_(b, l, m, EVAL_EXPR, r, false, null);
    return r;
  }

  // 'eval'|'do'
  private static boolean eval_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eval_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "eval");
    if (!r) r = consumeToken(b, "do");
    exit_section_(b, m, null, r);
    return r;
  }

  // block | expr
  private static boolean eval_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eval_expr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // package_definition | package_element
  public static boolean file_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<file item>");
    r = package_definition(b, l + 1);
    if (!r) r = package_element(b, l + 1);
    exit_section_(b, l, m, FILE_ITEM, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('foreach' | 'for') for_block_arguments
  public static boolean for_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<for block>");
    r = for_block_0(b, l + 1);
    r = r && for_block_arguments(b, l + 1);
    exit_section_(b, l, m, FOR_BLOCK, r, false, null);
    return r;
  }

  // 'foreach' | 'for'
  private static boolean for_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "foreach");
    if (!r) r = consumeToken(b, "for");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' expr ? PERL_SEMI expr ? PERL_SEMI expr ? ')' block
  //      | (variable_definition | variable ) ? '(' expr ')' block ('continue' block ) ?
  public static boolean for_block_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<for block arguments>");
    r = for_block_arguments_0(b, l + 1);
    if (!r) r = for_block_arguments_1(b, l + 1);
    exit_section_(b, l, m, FOR_BLOCK_ARGUMENTS, r, false, null);
    return r;
  }

  // '(' expr ? PERL_SEMI expr ? PERL_SEMI expr ? ')' block
  private static boolean for_block_arguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && for_block_arguments_0_1(b, l + 1);
    r = r && consumeToken(b, PERL_SEMI);
    r = r && for_block_arguments_0_3(b, l + 1);
    r = r && consumeToken(b, PERL_SEMI);
    r = r && for_block_arguments_0_5(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    r = r && block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean for_block_arguments_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_0_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  // expr ?
  private static boolean for_block_arguments_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_0_3")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  // expr ?
  private static boolean for_block_arguments_0_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_0_5")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  // (variable_definition | variable ) ? '(' expr ')' block ('continue' block ) ?
  private static boolean for_block_arguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = for_block_arguments_1_0(b, l + 1);
    r = r && consumeToken(b, PERL_LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RPAREN);
    r = r && block(b, l + 1);
    r = r && for_block_arguments_1_5(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (variable_definition | variable ) ?
  private static boolean for_block_arguments_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_1_0")) return false;
    for_block_arguments_1_0_0(b, l + 1);
    return true;
  }

  // variable_definition | variable
  private static boolean for_block_arguments_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable_definition(b, l + 1);
    if (!r) r = variable(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('continue' block ) ?
  private static boolean for_block_arguments_1_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_1_5")) return false;
    for_block_arguments_1_5_0(b, l + 1);
    return true;
  }

  // 'continue' block
  private static boolean for_block_arguments_1_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_block_arguments_1_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "continue");
    r = r && block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'given' block_conditional
  public static boolean given_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "given_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<given block>");
    r = consumeToken(b, "given");
    r = r && block_conditional(b, l + 1);
    exit_section_(b, l, m, GIVEN_BLOCK, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('grep' | 'map' ) (block list_expr | expr)
  public static boolean grep_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "grep_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<grep expr>");
    r = grep_expr_0(b, l + 1);
    r = r && grep_expr_1(b, l + 1);
    exit_section_(b, l, m, GREP_EXPR, r, false, null);
    return r;
  }

  // 'grep' | 'map'
  private static boolean grep_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "grep_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "grep");
    if (!r) r = consumeToken(b, "map");
    exit_section_(b, m, null, r);
    return r;
  }

  // block list_expr | expr
  private static boolean grep_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "grep_expr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = grep_expr_1_0(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // block list_expr
  private static boolean grep_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "grep_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block(b, l + 1);
    r = r && list_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_SIGIL_HASH '{' expr '}'   // hash dereference w braces
  //     | PERL_SIGIL_HASH scalar_expr       // scalar dereference wo braces
  //     | PERL_HASH
  public static boolean hash(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hash")) return false;
    if (!nextTokenIs(b, "<hash>", PERL_HASH, PERL_SIGIL_HASH)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<hash>");
    r = hash_0(b, l + 1);
    if (!r) r = hash_1(b, l + 1);
    if (!r) r = consumeToken(b, PERL_HASH);
    exit_section_(b, l, m, HASH, r, false, null);
    return r;
  }

  // PERL_SIGIL_HASH '{' expr '}'
  private static boolean hash_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hash_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_HASH);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SIGIL_HASH scalar_expr
  private static boolean hash_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hash_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_HASH);
    r = r && scalar_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '->'? '{' ( expr | PERL_BAREWORD) '}'
  static boolean hash_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hash_element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = hash_element_0(b, l + 1);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && hash_element_2(b, l + 1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // '->'?
  private static boolean hash_element_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hash_element_0")) return false;
    consumeToken(b, "->");
    return true;
  }

  // expr | PERL_BAREWORD
  private static boolean hash_element_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hash_element_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expr(b, l + 1, -1);
    if (!r) r = consumeToken(b, PERL_BAREWORD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('if' | 'unless') block_conditional if_block_elsif * if_block_else ?
  public static boolean if_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<if block>");
    r = if_block_0(b, l + 1);
    r = r && block_conditional(b, l + 1);
    r = r && if_block_2(b, l + 1);
    r = r && if_block_3(b, l + 1);
    exit_section_(b, l, m, IF_BLOCK, r, false, null);
    return r;
  }

  // 'if' | 'unless'
  private static boolean if_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "if");
    if (!r) r = consumeToken(b, "unless");
    exit_section_(b, m, null, r);
    return r;
  }

  // if_block_elsif *
  private static boolean if_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!if_block_elsif(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_block_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // if_block_else ?
  private static boolean if_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_3")) return false;
    if_block_else(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'else' block
  public static boolean if_block_else(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_else")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<if block else>");
    r = consumeToken(b, "else");
    r = r && block(b, l + 1);
    exit_section_(b, l, m, IF_BLOCK_ELSE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'elsif' block_conditional
  public static boolean if_block_elsif(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_elsif")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<if block elsif>");
    r = consumeToken(b, "elsif");
    r = r && block_conditional(b, l + 1);
    exit_section_(b, l, m, IF_BLOCK_ELSIF, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('if' | 'unless') expr
  public static boolean if_postfix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_postfix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<if postfix>");
    r = if_postfix_0(b, l + 1);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, l, m, IF_POSTFIX, r, false, null);
    return r;
  }

  // 'if' | 'unless'
  private static boolean if_postfix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_postfix_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "if");
    if (!r) r = consumeToken(b, "unless");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' (hash | array) ')'
  //     | (hash | array)
  public static boolean keys_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keys_args")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<keys args>");
    r = keys_args_0(b, l + 1);
    if (!r) r = keys_args_1(b, l + 1);
    exit_section_(b, l, m, KEYS_ARGS, r, false, null);
    return r;
  }

  // '(' (hash | array) ')'
  private static boolean keys_args_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keys_args_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && keys_args_0_1(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // hash | array
  private static boolean keys_args_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keys_args_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = hash(b, l + 1);
    if (!r) r = array(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // hash | array
  private static boolean keys_args_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keys_args_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = hash(b, l + 1);
    if (!r) r = array(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('keys' | 'values' ) keys_args
  public static boolean keys_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keys_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<keys expr>");
    r = keys_expr_0(b, l + 1);
    r = r && keys_args(b, l + 1);
    exit_section_(b, l, m, KEYS_EXPR, r, false, null);
    return r;
  }

  // 'keys' | 'values'
  private static boolean keys_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keys_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "keys");
    if (!r) r = consumeToken(b, "values");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_BAREWORD ":"
  static boolean label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label")) return false;
    if (!nextTokenIs(b, PERL_BAREWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_BAREWORD);
    r = r && consumeToken(b, ":");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('last' | 'next' | 'redo') expr ?
  public static boolean last_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<last expr>");
    r = last_expr_0(b, l + 1);
    r = r && last_expr_1(b, l + 1);
    exit_section_(b, l, m, LAST_EXPR, r, false, null);
    return r;
  }

  // 'last' | 'next' | 'redo'
  private static boolean last_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "last");
    if (!r) r = consumeToken(b, "next");
    if (!r) r = consumeToken(b, "redo");
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean last_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "last_expr_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // package_use
  //     | package_no
  //     | package_require
  //     | return_expr
  //     | last_expr
  //     | code_line
  static boolean line_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "line_element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_use(b, l + 1);
    if (!r) r = package_no(b, l + 1);
    if (!r) r = package_require(b, l + 1);
    if (!r) r = return_expr(b, l + 1);
    if (!r) r = last_expr(b, l + 1);
    if (!r) r = code_line(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // sort_expr
  //     | keys_expr
  //     | grep_expr
  //     | split_expr
  //     | qw_expr
  //     | '<' scalar_expr? '>' // bareword should be here too
  //     | '<' PERL_BAREWORD '>' // bareword should be here too
  //     | array
  //     | hash
  public static boolean list_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<list expr>");
    r = sort_expr(b, l + 1);
    if (!r) r = keys_expr(b, l + 1);
    if (!r) r = grep_expr(b, l + 1);
    if (!r) r = split_expr(b, l + 1);
    if (!r) r = qw_expr(b, l + 1);
    if (!r) r = list_expr_5(b, l + 1);
    if (!r) r = list_expr_6(b, l + 1);
    if (!r) r = array(b, l + 1);
    if (!r) r = hash(b, l + 1);
    exit_section_(b, l, m, LIST_EXPR, r, false, null);
    return r;
  }

  // '<' scalar_expr? '>'
  private static boolean list_expr_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_expr_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<");
    r = r && list_expr_5_1(b, l + 1);
    r = r && consumeToken(b, ">");
    exit_section_(b, m, null, r);
    return r;
  }

  // scalar_expr?
  private static boolean list_expr_5_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_expr_5_1")) return false;
    scalar_expr(b, l + 1);
    return true;
  }

  // '<' PERL_BAREWORD '>'
  private static boolean list_expr_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_expr_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<");
    r = r && consumeToken(b, PERL_BAREWORD);
    r = r && consumeToken(b, ">");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'local' local_variable_definition_arguments
  public static boolean local_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_definition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<local definition>");
    r = consumeToken(b, "local");
    r = r && local_variable_definition_arguments(b, l + 1);
    exit_section_(b, l, m, LOCAL_DEFINITION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // scalar_hash_element | variable
  static boolean local_variable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variable")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SCALAR_HASH_ELEMENT);
    if (!r) r = variable(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // perl_package ? local_variable | '(' local_variables ')'
  public static boolean local_variable_definition_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variable_definition_arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<local variable definition arguments>");
    r = local_variable_definition_arguments_0(b, l + 1);
    if (!r) r = local_variable_definition_arguments_1(b, l + 1);
    exit_section_(b, l, m, LOCAL_VARIABLE_DEFINITION_ARGUMENTS, r, false, null);
    return r;
  }

  // perl_package ? local_variable
  private static boolean local_variable_definition_arguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variable_definition_arguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = local_variable_definition_arguments_0_0(b, l + 1);
    r = r && local_variable(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_package ?
  private static boolean local_variable_definition_arguments_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variable_definition_arguments_0_0")) return false;
    perl_package(b, l + 1);
    return true;
  }

  // '(' local_variables ')'
  private static boolean local_variable_definition_arguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variable_definition_arguments_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && local_variables(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // local_variable (',' local_variables ) *
  static boolean local_variables(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variables")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = local_variable(b, l + 1);
    r = r && local_variables_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' local_variables ) *
  private static boolean local_variables_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variables_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!local_variables_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "local_variables_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' local_variables
  private static boolean local_variables_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_variables_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_COMMA);
    r = r && local_variables(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'm' match_regex
  static boolean m_regex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "m_regex")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "m");
    r = r && match_regex(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_REGEX_QUOTE perl_regex ? PERL_REGEX_QUOTE perl_regex_modifiers ?
  static boolean match_regex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_regex")) return false;
    if (!nextTokenIs(b, PERL_REGEX_QUOTE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_REGEX_QUOTE);
    r = r && match_regex_1(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    r = r && match_regex_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_regex ?
  private static boolean match_regex_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_regex_1")) return false;
    perl_regex(b, l + 1);
    return true;
  }

  // perl_regex_modifiers ?
  private static boolean match_regex_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_regex_3")) return false;
    perl_regex_modifiers(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '<<' (string | PERL_BAREWORD)
  static boolean multiline_marker(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiline_marker")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<<");
    r = r && multiline_marker_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // string | PERL_BAREWORD
  private static boolean multiline_marker_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiline_marker_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string(b, l + 1);
    if (!r) r = consumeToken(b, PERL_BAREWORD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'my' variable_definition_arguments
  public static boolean my_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "my_definition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<my definition>");
    r = consumeToken(b, "my");
    r = r && variable_definition_arguments(b, l + 1);
    exit_section_(b, l, m, MY_DEFINITION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // scalar_expr '->' perl_function
  public static boolean object_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_method")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<object method>");
    r = scalar_expr(b, l + 1);
    r = r && consumeToken(b, "->");
    r = r && perl_function(b, l + 1);
    exit_section_(b, l, m, OBJECT_METHOD, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // scalar_expr '->' scalar_expr
  public static boolean object_method_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_method_object")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<object method object>");
    r = scalar_expr(b, l + 1);
    r = r && consumeToken(b, "->");
    r = r && scalar_expr(b, l + 1);
    exit_section_(b, l, m, OBJECT_METHOD_OBJECT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'our' variable_definition_arguments
  public static boolean our_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "our_definition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<our definition>");
    r = consumeToken(b, "our");
    r = r && variable_definition_arguments(b, l + 1);
    exit_section_(b, l, m, OUR_DEFINITION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'package' perl_package perl_version ? (block | PERL_SEMI package_element * )
  public static boolean package_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_definition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package definition>");
    r = consumeToken(b, "package");
    r = r && perl_package(b, l + 1);
    r = r && package_definition_2(b, l + 1);
    r = r && package_definition_3(b, l + 1);
    exit_section_(b, l, m, PACKAGE_DEFINITION, r, false, null);
    return r;
  }

  // perl_version ?
  private static boolean package_definition_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_definition_2")) return false;
    perl_version(b, l + 1);
    return true;
  }

  // block | PERL_SEMI package_element *
  private static boolean package_definition_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_definition_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block(b, l + 1);
    if (!r) r = package_definition_3_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SEMI package_element *
  private static boolean package_definition_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_definition_3_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SEMI);
    r = r && package_definition_3_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // package_element *
  private static boolean package_definition_3_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_definition_3_1_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!package_element(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "package_definition_3_1_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // block_element (PERL_SEMI + | <<eof>>) ?
  //     | line_element if_postfix ? (PERL_SEMI + | <<eof>>) ?
  static boolean package_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_element_0(b, l + 1);
    if (!r) r = package_element_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // block_element (PERL_SEMI + | <<eof>>) ?
  private static boolean package_element_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block_element(b, l + 1);
    r = r && package_element_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (PERL_SEMI + | <<eof>>) ?
  private static boolean package_element_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_0_1")) return false;
    package_element_0_1_0(b, l + 1);
    return true;
  }

  // PERL_SEMI + | <<eof>>
  private static boolean package_element_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_element_0_1_0_0(b, l + 1);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SEMI +
  private static boolean package_element_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_0_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SEMI);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PERL_SEMI)) break;
      if (!empty_element_parsed_guard_(b, "package_element_0_1_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // line_element if_postfix ? (PERL_SEMI + | <<eof>>) ?
  private static boolean package_element_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = line_element(b, l + 1);
    r = r && package_element_1_1(b, l + 1);
    r = r && package_element_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // if_postfix ?
  private static boolean package_element_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_1_1")) return false;
    if_postfix(b, l + 1);
    return true;
  }

  // (PERL_SEMI + | <<eof>>) ?
  private static boolean package_element_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_1_2")) return false;
    package_element_1_2_0(b, l + 1);
    return true;
  }

  // PERL_SEMI + | <<eof>>
  private static boolean package_element_1_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_1_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = package_element_1_2_0_0(b, l + 1);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SEMI +
  private static boolean package_element_1_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_element_1_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SEMI);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PERL_SEMI)) break;
      if (!empty_element_parsed_guard_(b, "package_element_1_2_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // perl_package '->' perl_function
  public static boolean package_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_method")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package method>");
    r = perl_package(b, l + 1);
    r = r && consumeToken(b, "->");
    r = r && perl_function(b, l + 1);
    exit_section_(b, l, m, PACKAGE_METHOD, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'no' package_use_arguments
  public static boolean package_no(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_no")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package no>");
    r = consumeToken(b, "no");
    r = r && package_use_arguments(b, l + 1);
    exit_section_(b, l, m, PACKAGE_NO, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'require' (perl_package | perl_version | string)
  public static boolean package_require(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_require")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package require>");
    r = consumeToken(b, "require");
    r = r && package_require_1(b, l + 1);
    exit_section_(b, l, m, PACKAGE_REQUIRE, r, false, null);
    return r;
  }

  // perl_package | perl_version | string
  private static boolean package_require_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_require_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = perl_package(b, l + 1);
    if (!r) r = perl_version(b, l + 1);
    if (!r) r = string(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'use' package_use_arguments
  public static boolean package_use(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_use")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package use>");
    r = consumeToken(b, "use");
    r = r && package_use_arguments(b, l + 1);
    exit_section_(b, l, m, PACKAGE_USE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // perl_package perl_version expr ?
  //     | perl_package expr ?
  //     | perl_version
  public static boolean package_use_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_use_arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<package use arguments>");
    r = package_use_arguments_0(b, l + 1);
    if (!r) r = package_use_arguments_1(b, l + 1);
    if (!r) r = perl_version(b, l + 1);
    exit_section_(b, l, m, PACKAGE_USE_ARGUMENTS, r, false, null);
    return r;
  }

  // perl_package perl_version expr ?
  private static boolean package_use_arguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_use_arguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = perl_package(b, l + 1);
    r = r && perl_version(b, l + 1);
    r = r && package_use_arguments_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean package_use_arguments_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_use_arguments_0_2")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  // perl_package expr ?
  private static boolean package_use_arguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_use_arguments_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = perl_package(b, l + 1);
    r = r && package_use_arguments_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean package_use_arguments_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_use_arguments_1_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // file_item*
  static boolean perlFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perlFile")) return false;
    int c = current_position_(b);
    while (true) {
      if (!file_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "perlFile", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // <<parseBarewordFunction>>
  static boolean perl_function(PsiBuilder b, int l) {
    return parseBarewordFunction(b, l + 1);
  }

  /* ********************************************************** */
  // '(' perl_function_proto_sigil ? ')'
  static boolean perl_function_proto(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_function_proto")) return false;
    if (!nextTokenIs(b, PERL_LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && perl_function_proto_1(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_function_proto_sigil ?
  private static boolean perl_function_proto_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_function_proto_1")) return false;
    perl_function_proto_sigil(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '$'|'%'|'@'|';' +
  static boolean perl_function_proto_sigil(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_function_proto_sigil")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_SCALAR);
    if (!r) r = consumeToken(b, PERL_SIGIL_HASH);
    if (!r) r = consumeToken(b, PERL_SIGIL_ARRAY);
    if (!r) r = perl_function_proto_sigil_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ';' +
  private static boolean perl_function_proto_sigil_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_function_proto_sigil_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SEMI);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PERL_SEMI)) break;
      if (!empty_element_parsed_guard_(b, "perl_function_proto_sigil_3", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // scalar_expr '->' <<parsePackageMethodSuper>>
  static boolean perl_method_parent(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_method_parent")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scalar_expr(b, l + 1);
    r = r && consumeToken(b, "->");
    r = r && parsePackageMethodSuper(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<parseBarewordPackage>>
  static boolean perl_package(PsiBuilder b, int l) {
    return parseBarewordPackage(b, l + 1);
  }

  /* ********************************************************** */
  // <<parsePackageFunctionCall>>
  static boolean perl_package_function(PsiBuilder b, int l) {
    return parsePackageFunctionCall(b, l + 1);
  }

  /* ********************************************************** */
  // PERL_REGEX_TOKEN +
  public static boolean perl_regex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_regex")) return false;
    if (!nextTokenIs(b, PERL_REGEX_TOKEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_REGEX_TOKEN);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PERL_REGEX_TOKEN)) break;
      if (!empty_element_parsed_guard_(b, "perl_regex", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, PERL_REGEX, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_REGEX_MODIFIER +
  public static boolean perl_regex_modifiers(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_regex_modifiers")) return false;
    if (!nextTokenIs(b, PERL_REGEX_MODIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_REGEX_MODIFIER);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PERL_REGEX_MODIFIER)) break;
      if (!empty_element_parsed_guard_(b, "perl_regex_modifiers", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, PERL_REGEX_MODIFIERS, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_NUMBER_VERSION | PERL_NUMBER
  public static boolean perl_version(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "perl_version")) return false;
    if (!nextTokenIs(b, "<perl version>", PERL_NUMBER, PERL_NUMBER_VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<perl version>");
    r = consumeToken(b, PERL_NUMBER_VERSION);
    if (!r) r = consumeToken(b, PERL_NUMBER);
    exit_section_(b, l, m, PERL_VERSION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'qr' match_regex
  static boolean qr_regex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qr_regex")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "qr");
    r = r && match_regex(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'qw' PERL_QUOTE PERL_STRING_CONTENT * PERL_QUOTE
  public static boolean qw_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qw_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<qw expr>");
    r = consumeToken(b, "qw");
    r = r && consumeToken(b, PERL_QUOTE);
    r = r && qw_expr_2(b, l + 1);
    r = r && consumeToken(b, PERL_QUOTE);
    exit_section_(b, l, m, QW_EXPR, r, false, null);
    return r;
  }

  // PERL_STRING_CONTENT *
  private static boolean qw_expr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qw_expr_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PERL_STRING_CONTENT)) break;
      if (!empty_element_parsed_guard_(b, "qw_expr_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // '&'
  //     (
  //         perl_package_function
  //         | perl_function
  //         | {scalar_expr}
  //         | scalar_expr
  //     )
  static boolean referencable_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "referencable_method")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "&");
    r = r && referencable_method_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_package_function
  //         | perl_function
  //         | {scalar_expr}
  //         | scalar_expr
  private static boolean referencable_method_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "referencable_method_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = perl_package_function(b, l + 1);
    if (!r) r = perl_function(b, l + 1);
    if (!r) r = referencable_method_1_2(b, l + 1);
    if (!r) r = scalar_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // {scalar_expr}
  private static boolean referencable_method_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "referencable_method_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scalar_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // m_regex
  //     | qr_regex
  //     | s_regex
  //     | tr_regex
  //     | match_regex
  public static boolean regex_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "regex_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<regex expr>");
    r = m_regex(b, l + 1);
    if (!r) r = qr_regex(b, l + 1);
    if (!r) r = s_regex(b, l + 1);
    if (!r) r = tr_regex(b, l + 1);
    if (!r) r = match_regex(b, l + 1);
    exit_section_(b, l, m, REGEX_EXPR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('return' | 'exit') expr ?
  public static boolean return_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<return expr>");
    r = return_expr_0(b, l + 1);
    r = r && return_expr_1(b, l + 1);
    exit_section_(b, l, m, RETURN_EXPR, r, false, null);
    return r;
  }

  // 'return' | 'exit'
  private static boolean return_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "return");
    if (!r) r = consumeToken(b, "exit");
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean return_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_expr_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // 's' PERL_REGEX_QUOTE perl_regex ? PERL_REGEX_QUOTE s_regex_replacement perl_regex_modifiers ?
  static boolean s_regex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "s");
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    r = r && s_regex_2(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    r = r && s_regex_replacement(b, l + 1);
    r = r && s_regex_5(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_regex ?
  private static boolean s_regex_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_2")) return false;
    perl_regex(b, l + 1);
    return true;
  }

  // perl_regex_modifiers ?
  private static boolean s_regex_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_5")) return false;
    perl_regex_modifiers(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PERL_REGEX_QUOTE perl_regex ? PERL_REGEX_QUOTE
  //     | perl_regex ? PERL_REGEX_QUOTE
  static boolean s_regex_replacement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_replacement")) return false;
    if (!nextTokenIs(b, "", PERL_REGEX_QUOTE, PERL_REGEX_TOKEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = s_regex_replacement_0(b, l + 1);
    if (!r) r = s_regex_replacement_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_REGEX_QUOTE perl_regex ? PERL_REGEX_QUOTE
  private static boolean s_regex_replacement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_replacement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_REGEX_QUOTE);
    r = r && s_regex_replacement_0_1(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_regex ?
  private static boolean s_regex_replacement_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_replacement_0_1")) return false;
    perl_regex(b, l + 1);
    return true;
  }

  // perl_regex ? PERL_REGEX_QUOTE
  private static boolean s_regex_replacement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_replacement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = s_regex_replacement_1_0(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_regex ?
  private static boolean s_regex_replacement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_regex_replacement_1_0")) return false;
    perl_regex(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PERL_SIGIL_SCALAR '{' PERL_BAREWORD '}'       // scalar dereference
  //     | PERL_SIGIL_SCALAR '{' expr '}'       // scalar dereference
  //     | PERL_SCALAR                          // scalar as is
  //     | PERL_NUMBER                       // raw number
  //     | PERL_TAG
  //     | PERL_GLOB
  //     | scalar_anon_array
  //     | scalar_anon_hash
  //     | scalar_generated_list_item
  //     | multiline_marker                  // deferred string
  //     | string                            // string
  //     | sub_block_anon
  //     | referencable_method
  //     | 'undef'
  static boolean scalar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scalar_0(b, l + 1);
    if (!r) r = scalar_1(b, l + 1);
    if (!r) r = consumeToken(b, PERL_SCALAR);
    if (!r) r = consumeToken(b, PERL_NUMBER);
    if (!r) r = consumeToken(b, PERL_TAG);
    if (!r) r = consumeToken(b, PERL_GLOB);
    if (!r) r = scalar_anon_array(b, l + 1);
    if (!r) r = scalar_anon_hash(b, l + 1);
    if (!r) r = scalar_generated_list_item(b, l + 1);
    if (!r) r = multiline_marker(b, l + 1);
    if (!r) r = string(b, l + 1);
    if (!r) r = sub_block_anon(b, l + 1);
    if (!r) r = referencable_method(b, l + 1);
    if (!r) r = consumeToken(b, "undef");
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SIGIL_SCALAR '{' PERL_BAREWORD '}'
  private static boolean scalar_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_SCALAR);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && consumeToken(b, PERL_BAREWORD);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_SIGIL_SCALAR '{' expr '}'
  private static boolean scalar_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SIGIL_SCALAR);
    r = r && consumeToken(b, PERL_LBRACE);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '[' expr ? ']'
  static boolean scalar_anon_array(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_anon_array")) return false;
    if (!nextTokenIs(b, PERL_LBRACK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LBRACK);
    r = r && scalar_anon_array_1(b, l + 1);
    r = r && consumeToken(b, PERL_RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean scalar_anon_array_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_anon_array_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // '{' expr ? '}'
  static boolean scalar_anon_hash(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_anon_hash")) return false;
    if (!nextTokenIs(b, PERL_LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LBRACE);
    r = r && scalar_anon_hash_1(b, l + 1);
    r = r && consumeToken(b, PERL_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // expr ?
  private static boolean scalar_anon_hash_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_anon_hash_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // shift_expr
  //     | package_require
  //     | return_expr
  //     | eval_expr
  //     | regex_expr
  //     | scalar_nested_element
  //     | scalar
  public static boolean scalar_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<scalar expr>");
    r = shift_expr(b, l + 1);
    if (!r) r = package_require(b, l + 1);
    if (!r) r = return_expr(b, l + 1);
    if (!r) r = eval_expr(b, l + 1);
    if (!r) r = regex_expr(b, l + 1);
    if (!r) r = scalar_nested_element(b, l + 1);
    if (!r) r = scalar(b, l + 1);
    exit_section_(b, l, m, SCALAR_EXPR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '(' expr ')'[expr]
  static boolean scalar_generated_list_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_generated_list_item")) return false;
    if (!nextTokenIs(b, PERL_LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RPAREN);
    r = r && scalar_generated_list_item_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [expr]
  private static boolean scalar_generated_list_item_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_generated_list_item_3")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // scalar structure_element+
  public static boolean scalar_nested_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_nested_element")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<scalar nested element>");
    r = scalar(b, l + 1);
    r = r && scalar_nested_element_1(b, l + 1);
    exit_section_(b, l, m, SCALAR_NESTED_ELEMENT, r, false, null);
    return r;
  }

  // structure_element+
  private static boolean scalar_nested_element_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scalar_nested_element_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = structure_element(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!structure_element(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "scalar_nested_element_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('shift' | 'pop') shift_expr_args ?
  public static boolean shift_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<shift expr>");
    r = shift_expr_0(b, l + 1);
    r = r && shift_expr_1(b, l + 1);
    exit_section_(b, l, m, SHIFT_EXPR, r, false, null);
    return r;
  }

  // 'shift' | 'pop'
  private static boolean shift_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "shift");
    if (!r) r = consumeToken(b, "pop");
    exit_section_(b, m, null, r);
    return r;
  }

  // shift_expr_args ?
  private static boolean shift_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_expr_1")) return false;
    shift_expr_args(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // list_expr | expr
  static boolean shift_expr_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_expr_args")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = list_expr(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'sort' sort_op_args
  public static boolean sort_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<sort expr>");
    r = consumeToken(b, "sort");
    r = r && sort_op_args(b, l + 1);
    exit_section_(b, l, m, SORT_EXPR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // perl_function list_expr
  //     | block ? list_expr
  public static boolean sort_op_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort_op_args")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<sort op args>");
    r = sort_op_args_0(b, l + 1);
    if (!r) r = sort_op_args_1(b, l + 1);
    exit_section_(b, l, m, SORT_OP_ARGS, r, false, null);
    return r;
  }

  // perl_function list_expr
  private static boolean sort_op_args_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort_op_args_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = perl_function(b, l + 1);
    r = r && list_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // block ? list_expr
  private static boolean sort_op_args_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort_op_args_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sort_op_args_1_0(b, l + 1);
    r = r && list_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // block ?
  private static boolean sort_op_args_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sort_op_args_1_0")) return false;
    block(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (scalar_expr | regex_expr ) "," scalar_expr
  public static boolean split_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "split_args")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<split args>");
    r = split_args_0(b, l + 1);
    r = r && consumeToken(b, PERL_COMMA);
    r = r && scalar_expr(b, l + 1);
    exit_section_(b, l, m, SPLIT_ARGS, r, false, null);
    return r;
  }

  // scalar_expr | regex_expr
  private static boolean split_args_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "split_args_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = scalar_expr(b, l + 1);
    if (!r) r = regex_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "(" split_args ")"
  //     | split_args
  static boolean split_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "split_arguments")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = split_arguments_0(b, l + 1);
    if (!r) r = split_args(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "(" split_args ")"
  private static boolean split_arguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "split_arguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && split_args(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'split' split_arguments
  public static boolean split_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "split_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<split expr>");
    r = consumeToken(b, "split");
    r = r && split_arguments(b, l + 1);
    exit_section_(b, l, m, SPLIT_EXPR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // string_quoted | string_unquoted | PERL_STRING_CONTENT | <<parseBarewordString>>
  static boolean string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string_quoted(b, l + 1);
    if (!r) r = string_unquoted(b, l + 1);
    if (!r) r = consumeToken(b, PERL_STRING_CONTENT);
    if (!r) r = parseBarewordString(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('qq' | 'qx' | 'q') ? PERL_QUOTE PERL_STRING_CONTENT PERL_QUOTE
  static boolean string_quoted(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_quoted")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string_quoted_0(b, l + 1);
    r = r && consumeTokens(b, 0, PERL_QUOTE, PERL_STRING_CONTENT, PERL_QUOTE);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('qq' | 'qx' | 'q') ?
  private static boolean string_quoted_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_quoted_0")) return false;
    string_quoted_0_0(b, l + 1);
    return true;
  }

  // 'qq' | 'qx' | 'q'
  private static boolean string_quoted_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_quoted_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "qq");
    if (!r) r = consumeToken(b, "qx");
    if (!r) r = consumeToken(b, "q");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_STRING_CONTENT
  static boolean string_unquoted(PsiBuilder b, int l) {
    return consumeToken(b, PERL_STRING_CONTENT);
  }

  /* ********************************************************** */
  // array_element | hash_element
  static boolean structure_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_element(b, l + 1);
    if (!r) r = hash_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'sub' perl_function_proto ?  block
  public static boolean sub_block_anon(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_block_anon")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<sub block anon>");
    r = consumeToken(b, "sub");
    r = r && sub_block_anon_1(b, l + 1);
    r = r && block(b, l + 1);
    exit_section_(b, l, m, SUB_BLOCK_ANON, r, false, null);
    return r;
  }

  // perl_function_proto ?
  private static boolean sub_block_anon_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_block_anon_1")) return false;
    perl_function_proto(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'sub' perl_function perl_function_proto ? block
  public static boolean sub_block_named(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_block_named")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<sub block named>");
    r = consumeToken(b, "sub");
    r = r && perl_function(b, l + 1);
    r = r && sub_block_named_2(b, l + 1);
    r = r && block(b, l + 1);
    exit_section_(b, l, m, SUB_BLOCK_NAMED, r, false, null);
    return r;
  }

  // perl_function_proto ?
  private static boolean sub_block_named_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_block_named_2")) return false;
    perl_function_proto(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '(' expr ')'
  //     | list_expr
  //     | scalar_expr
  public static boolean term(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<term>");
    r = term_0(b, l + 1);
    if (!r) r = list_expr(b, l + 1);
    if (!r) r = scalar_expr(b, l + 1);
    exit_section_(b, l, m, TERM, r, false, null);
    return r;
  }

  // '(' expr ')'
  private static boolean term_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_REGEX_MODIFIER +
  public static boolean tr_modifiers(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_modifiers")) return false;
    if (!nextTokenIs(b, PERL_REGEX_MODIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_REGEX_MODIFIER);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PERL_REGEX_MODIFIER)) break;
      if (!empty_element_parsed_guard_(b, "tr_modifiers", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, TR_MODIFIERS, r);
    return r;
  }

  /* ********************************************************** */
  // ('tr'|'y') PERL_REGEX_QUOTE tr_searchlist PERL_REGEX_QUOTE PERL_REGEX_QUOTE ? tr_replacementlist PERL_REGEX_QUOTE tr_modifiers ?
  public static boolean tr_regex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_regex")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<tr regex>");
    r = tr_regex_0(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    r = r && tr_searchlist(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    r = r && tr_regex_4(b, l + 1);
    r = r && tr_replacementlist(b, l + 1);
    r = r && consumeToken(b, PERL_REGEX_QUOTE);
    r = r && tr_regex_7(b, l + 1);
    exit_section_(b, l, m, TR_REGEX, r, false, null);
    return r;
  }

  // 'tr'|'y'
  private static boolean tr_regex_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_regex_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "tr");
    if (!r) r = consumeToken(b, "y");
    exit_section_(b, m, null, r);
    return r;
  }

  // PERL_REGEX_QUOTE ?
  private static boolean tr_regex_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_regex_4")) return false;
    consumeToken(b, PERL_REGEX_QUOTE);
    return true;
  }

  // tr_modifiers ?
  private static boolean tr_regex_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_regex_7")) return false;
    tr_modifiers(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PERL_STRING_CONTENT
  public static boolean tr_replacementlist(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_replacementlist")) return false;
    if (!nextTokenIs(b, PERL_STRING_CONTENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_STRING_CONTENT);
    exit_section_(b, m, TR_REPLACEMENTLIST, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_STRING_CONTENT
  public static boolean tr_searchlist(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tr_searchlist")) return false;
    if (!nextTokenIs(b, PERL_STRING_CONTENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_STRING_CONTENT);
    exit_section_(b, m, TR_SEARCHLIST, r);
    return r;
  }

  /* ********************************************************** */
  // PERL_SCALAR | PERL_ARRAY | PERL_HASH | PERL_GLOB | "undef"
  static boolean variable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_SCALAR);
    if (!r) r = consumeToken(b, PERL_ARRAY);
    if (!r) r = consumeToken(b, PERL_HASH);
    if (!r) r = consumeToken(b, PERL_GLOB);
    if (!r) r = consumeToken(b, "undef");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // my_definition | our_definition | local_definition
  public static boolean variable_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<variable definition>");
    r = my_definition(b, l + 1);
    if (!r) r = our_definition(b, l + 1);
    if (!r) r = local_definition(b, l + 1);
    exit_section_(b, l, m, VARIABLE_DEFINITION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // perl_package ? variable | '(' variables ')'
  public static boolean variable_definition_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<variable definition arguments>");
    r = variable_definition_arguments_0(b, l + 1);
    if (!r) r = variable_definition_arguments_1(b, l + 1);
    exit_section_(b, l, m, VARIABLE_DEFINITION_ARGUMENTS, r, false, null);
    return r;
  }

  // perl_package ? variable
  private static boolean variable_definition_arguments_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_arguments_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable_definition_arguments_0_0(b, l + 1);
    r = r && variable(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // perl_package ?
  private static boolean variable_definition_arguments_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_arguments_0_0")) return false;
    perl_package(b, l + 1);
    return true;
  }

  // '(' variables ')'
  private static boolean variable_definition_arguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_definition_arguments_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_LPAREN);
    r = r && variables(b, l + 1);
    r = r && consumeToken(b, PERL_RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // variable (',' variable ) *
  static boolean variables(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variables")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = variable(b, l + 1);
    r = r && variables_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' variable ) *
  private static boolean variables_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variables_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!variables_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variables_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' variable
  private static boolean variables_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variables_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERL_COMMA);
    r = r && variable(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('until' | 'while' ) block_conditional ('continue' block ) ?
  public static boolean while_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<while block>");
    r = while_block_0(b, l + 1);
    r = r && block_conditional(b, l + 1);
    r = r && while_block_2(b, l + 1);
    exit_section_(b, l, m, WHILE_BLOCK, r, false, null);
    return r;
  }

  // 'until' | 'while'
  private static boolean while_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_block_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "until");
    if (!r) r = consumeToken(b, "while");
    exit_section_(b, m, null, r);
    return r;
  }

  // ('continue' block ) ?
  private static boolean while_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_block_2")) return false;
    while_block_2_0(b, l + 1);
    return true;
  }

  // 'continue' block
  private static boolean while_block_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_block_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "continue");
    r = r && block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Expression root: expr
  // Operator priority table:
  // 0: BINARY(op_24_expr)
  // 1: BINARY(op_23_expr)
  // 2: PREFIX(op_22_expr)
  // 3: ATOM(op_21_expr)
  // 4: BINARY(op_20_expr)
  // 5: BINARY(op_19_expr)
  // 6: BINARY(op_18_expr)
  // 7: BINARY(op_17_expr)
  // 8: BINARY(op_16_expr)
  // 9: BINARY(op_15_expr)
  // 10: BINARY(op_14_expr)
  // 11: BINARY(op_13_expr)
  // 12: BINARY(op_12_expr)
  // 13: BINARY(op_11_expr)
  // 14: PREFIX(op_10_expr)
  // 15: BINARY(op_9_expr)
  // 16: BINARY(op_8_expr)
  // 17: BINARY(op_7_expr)
  // 18: BINARY(op_6_expr)
  // 19: PREFIX(op_5_expr)
  // 20: N_ARY(op_4_expr)
  // 21: PREFIX(op_3_pref_expr) POSTFIX(op_3_suff_expr)
  // 22: BINARY(op_2_expr)
  // 23: ATOM(op_1_expr)
  public static boolean expr(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr")) return false;
    addVariant(b, "<expr>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expr>");
    r = op_22_expr(b, l + 1);
    if (!r) r = op_21_expr(b, l + 1);
    if (!r) r = op_10_expr(b, l + 1);
    if (!r) r = op_5_expr(b, l + 1);
    if (!r) r = op_3_pref_expr(b, l + 1);
    if (!r) r = op_1_expr(b, l + 1);
    p = r;
    r = r && expr_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expr_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && op_24_expr_0(b, l + 1)) {
        r = expr(b, l, 0);
        exit_section_(b, l, m, OP_24_EXPR, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, "and")) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, OP_23_EXPR, r, true, null);
      }
      else if (g < 4 && op_20_expr_0(b, l + 1)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, OP_20_EXPR, r, true, null);
      }
      else if (g < 5 && op_19_expr_0(b, l + 1)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, OP_19_EXPR, r, true, null);
      }
      else if (g < 6 && consumeTokenSmart(b, "?")) {
        r = report_error_(b, expr(b, l, 5));
        r = op_18_expr_1(b, l + 1) && r;
        exit_section_(b, l, m, OP_18_EXPR, r, true, null);
      }
      else if (g < 7 && op_17_expr_0(b, l + 1)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, OP_17_EXPR, r, true, null);
      }
      else if (g < 8 && op_16_expr_0(b, l + 1)) {
        r = expr(b, l, 8);
        exit_section_(b, l, m, OP_16_EXPR, r, true, null);
      }
      else if (g < 9 && consumeTokenSmart(b, "&&")) {
        r = expr(b, l, 9);
        exit_section_(b, l, m, OP_15_EXPR, r, true, null);
      }
      else if (g < 10 && op_14_expr_0(b, l + 1)) {
        r = expr(b, l, 10);
        exit_section_(b, l, m, OP_14_EXPR, r, true, null);
      }
      else if (g < 11 && consumeTokenSmart(b, "&")) {
        r = expr(b, l, 11);
        exit_section_(b, l, m, OP_13_EXPR, r, true, null);
      }
      else if (g < 12 && op_12_expr_0(b, l + 1)) {
        r = expr(b, l, 12);
        exit_section_(b, l, m, OP_12_EXPR, r, true, null);
      }
      else if (g < 13 && op_11_expr_0(b, l + 1)) {
        r = expr(b, l, 13);
        exit_section_(b, l, m, OP_11_EXPR, r, true, null);
      }
      else if (g < 15 && op_9_expr_0(b, l + 1)) {
        r = expr(b, l, 15);
        exit_section_(b, l, m, OP_9_EXPR, r, true, null);
      }
      else if (g < 16 && op_8_expr_0(b, l + 1)) {
        r = expr(b, l, 16);
        exit_section_(b, l, m, OP_8_EXPR, r, true, null);
      }
      else if (g < 17 && op_7_expr_0(b, l + 1)) {
        r = expr(b, l, 17);
        exit_section_(b, l, m, OP_7_EXPR, r, true, null);
      }
      else if (g < 18 && op_6_expr_0(b, l + 1)) {
        r = expr(b, l, 18);
        exit_section_(b, l, m, OP_6_EXPR, r, true, null);
      }
      else if (g < 20 && consumeTokenSmart(b, "**")) {
        while (true) {
          r = report_error_(b, expr(b, l, 20));
          if (!consumeTokenSmart(b, "**")) break;
        }
        exit_section_(b, l, m, OP_4_EXPR, r, true, null);
      }
      else if (g < 21 && op_3_suff_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, OP_3_SUFF_EXPR, r, true, null);
      }
      else if (g < 22 && consumeTokenSmart(b, "->")) {
        r = expr(b, l, 22);
        exit_section_(b, l, m, OP_2_EXPR, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // 'or'|'xor'
  private static boolean op_24_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_24_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "or");
    if (!r) r = consumeTokenSmart(b, "xor");
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean op_22_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_22_expr")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, "not");
    p = r;
    r = p && expr(b, l, 2);
    exit_section_(b, l, m, OP_22_EXPR, r, p, null);
    return r || p;
  }

  // call_rightward
  public static boolean op_21_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_21_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<op 21 expr>");
    r = call_rightward(b, l + 1);
    exit_section_(b, l, m, OP_21_EXPR, r, false, null);
    return r;
  }

  // ',' | '=>'
  private static boolean op_20_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_20_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, PERL_COMMA);
    if (!r) r = consumeTokenSmart(b, PERL_ARROW_COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // '=' | '**='|'+='|'-='| '*='|'/='|'x='| '&='|'|='|'.='| '<<='|'>>='|'%='| '&&='|'||='|'^='| '//='
  private static boolean op_19_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_19_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "=");
    if (!r) r = consumeTokenSmart(b, "**=");
    if (!r) r = consumeTokenSmart(b, "+=");
    if (!r) r = consumeTokenSmart(b, "-=");
    if (!r) r = consumeTokenSmart(b, "*=");
    if (!r) r = consumeTokenSmart(b, "/=");
    if (!r) r = consumeTokenSmart(b, "x=");
    if (!r) r = consumeTokenSmart(b, "&=");
    if (!r) r = consumeTokenSmart(b, "|=");
    if (!r) r = consumeTokenSmart(b, ".=");
    if (!r) r = consumeTokenSmart(b, "<<=");
    if (!r) r = consumeTokenSmart(b, ">>=");
    if (!r) r = consumeTokenSmart(b, "%=");
    if (!r) r = consumeTokenSmart(b, "&&=");
    if (!r) r = consumeTokenSmart(b, "||=");
    if (!r) r = consumeTokenSmart(b, "^=");
    if (!r) r = consumeTokenSmart(b, "//=");
    exit_section_(b, m, null, r);
    return r;
  }

  // ':' expr
  private static boolean op_18_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_18_expr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ":");
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '..'|'...'
  private static boolean op_17_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_17_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "..");
    if (!r) r = consumeTokenSmart(b, "...");
    exit_section_(b, m, null, r);
    return r;
  }

  // '||'|'//'
  private static boolean op_16_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_16_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "||");
    if (!r) r = consumeTokenSmart(b, "//");
    exit_section_(b, m, null, r);
    return r;
  }

  // '|'|'^'
  private static boolean op_14_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_14_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "|");
    if (!r) r = consumeTokenSmart(b, "^");
    exit_section_(b, m, null, r);
    return r;
  }

  // '=='|'!='|'<=>'|'eq'|'ne'|'cmp'|'~~'
  private static boolean op_12_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_12_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "==");
    if (!r) r = consumeTokenSmart(b, "!=");
    if (!r) r = consumeTokenSmart(b, "<=>");
    if (!r) r = consumeTokenSmart(b, "eq");
    if (!r) r = consumeTokenSmart(b, "ne");
    if (!r) r = consumeTokenSmart(b, "cmp");
    if (!r) r = consumeTokenSmart(b, "~~");
    exit_section_(b, m, null, r);
    return r;
  }

  // '>='|'<='|'<'|'>'|'lt'|'gt'|'le'|'ge'
  private static boolean op_11_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_11_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, ">=");
    if (!r) r = consumeTokenSmart(b, "<=");
    if (!r) r = consumeTokenSmart(b, "<");
    if (!r) r = consumeTokenSmart(b, ">");
    if (!r) r = consumeTokenSmart(b, "lt");
    if (!r) r = consumeTokenSmart(b, "gt");
    if (!r) r = consumeTokenSmart(b, "le");
    if (!r) r = consumeTokenSmart(b, "ge");
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean op_10_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_10_expr")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = op_10_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 14);
    exit_section_(b, l, m, OP_10_EXPR, r, p, null);
    return r || p;
  }

  // 'not'|'defined'|'ref'|'exists'|'scalar'
  private static boolean op_10_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_10_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "not");
    if (!r) r = consumeTokenSmart(b, "defined");
    if (!r) r = consumeTokenSmart(b, "ref");
    if (!r) r = consumeTokenSmart(b, "exists");
    if (!r) r = consumeTokenSmart(b, "scalar");
    exit_section_(b, m, null, r);
    return r;
  }

  // '<<'|'>>'
  private static boolean op_9_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_9_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "<<");
    if (!r) r = consumeTokenSmart(b, ">>");
    exit_section_(b, m, null, r);
    return r;
  }

  // '+'|'-'|'.'
  private static boolean op_8_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_8_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "+");
    if (!r) r = consumeTokenSmart(b, "-");
    if (!r) r = consumeTokenSmart(b, ".");
    exit_section_(b, m, null, r);
    return r;
  }

  // '*'|'/'|'%'|'x'
  private static boolean op_7_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_7_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "*");
    if (!r) r = consumeTokenSmart(b, "/");
    if (!r) r = consumeTokenSmart(b, PERL_SIGIL_HASH);
    if (!r) r = consumeTokenSmart(b, "x");
    exit_section_(b, m, null, r);
    return r;
  }

  // '=~'|'!~'
  private static boolean op_6_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_6_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "=~");
    if (!r) r = consumeTokenSmart(b, "!~");
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean op_5_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_5_expr")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = op_5_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 19);
    exit_section_(b, l, m, OP_5_EXPR, r, p, null);
    return r || p;
  }

  // '\' | '~'| '!'| '+' | '-'
  private static boolean op_5_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_5_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "\\");
    if (!r) r = consumeTokenSmart(b, "~");
    if (!r) r = consumeTokenSmart(b, "!");
    if (!r) r = consumeTokenSmart(b, "+");
    if (!r) r = consumeTokenSmart(b, "-");
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean op_3_pref_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_3_pref_expr")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = op_3_pref_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 21);
    exit_section_(b, l, m, OP_3_PREF_EXPR, r, p, null);
    return r || p;
  }

  // '++'|'--'
  private static boolean op_3_pref_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_3_pref_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "++");
    if (!r) r = consumeTokenSmart(b, "--");
    exit_section_(b, m, null, r);
    return r;
  }

  // '++'|'--'
  private static boolean op_3_suff_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_3_suff_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, "++");
    if (!r) r = consumeTokenSmart(b, "--");
    exit_section_(b, m, null, r);
    return r;
  }

  // term | call_leftward | variable_definition
  public static boolean op_1_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "op_1_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<op 1 expr>");
    r = term(b, l + 1);
    if (!r) r = call_leftward(b, l + 1);
    if (!r) r = variable_definition(b, l + 1);
    exit_section_(b, l, m, OP_1_EXPR, r, false, null);
    return r;
  }

}
