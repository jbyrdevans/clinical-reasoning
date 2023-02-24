package org.opencds.cqf.cql.evaluator.questionnaire.dstu3;

import static org.testng.Assert.assertThrows;

import org.hl7.fhir.dstu3.model.Parameters;
import org.hl7.fhir.dstu3.model.StringType;
import org.testng.annotations.Test;

public class QuestionnaireProcessorTests {
  @Test(enabled = false) // Need valid dstu3 content for this test
  void testPrePopulate() {
    TestQuestionnaire.Assert.that("questionnaire-for-order.json", "OPA-Patient1")
        .withData("o2_peter_bundle.json").withLibrary("outpatientPA.json")
        .withParameters(new Parameters()
            .addParameter(new Parameters.ParametersParameterComponent(new StringType("ClaimId"))
                .setValue(new StringType("OPA-Claim1"))))
        .prePopulate().isEqualsTo("questionnaire-for-order-populated.json");
  }

  @Test
  void testPrePopulate_NoLibrary() {
    TestQuestionnaire.Assert.that("questionnaire-for-order.json", "OPA-Patient1")
        .withData("o2_peter_bundle.json")
        .withParameters(new Parameters()
            .addParameter(new Parameters.ParametersParameterComponent(new StringType("ClaimId"))
                .setValue(new StringType("OPA-Claim1"))))
        .prePopulate().isEqualsTo("questionnaire-for-order-populated-noLibrary.json");
  }

  @Test
  void testPrePopulate_HasErrors() {
    TestQuestionnaire.Assert.that("questionnaire-for-order-errors.json", "OPA-Patient1")
        .withData("o2_peter_bundle.json").withLibrary("outpatientPA.json")
        .withParameters(new Parameters()
            .addParameter(new Parameters.ParametersParameterComponent(new StringType("ClaimId"))
                .setValue(new StringType("OPA-Claim1"))))
        .prePopulate().isEqualsTo("questionnaire-for-order-populated-errors.json");
  }

  @Test
  void testPrePopulate_noQuestionnaire_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      TestQuestionnaire.Assert.that("", null).prePopulate();
    });
  }

  @Test
  void testPrePopulate_notQuestionnaire_throwsException() {
    assertThrows(ClassCastException.class, () -> {
      TestQuestionnaire.Assert.that("invalid-questionnaire.json", null).prePopulate();
    });
  }

  @Test(enabled = false) // Need valid dstu3 content for this test
  void testPopulate() {
    TestQuestionnaire.Assert.that("questionnaire-for-order.json", "OPA-Patient1")
        .withData("o2_peter_bundle.json").withLibrary("outpatientPA.json")
        .withParameters(new Parameters()
            .addParameter(new Parameters.ParametersParameterComponent(new StringType("ClaimId"))
                .setValue(new StringType("OPA-Claim1"))))
        .populate().isEqualsTo("questionnaire-response-populated.json");
  }

  @Test
  void testPopulate_NoLibrary() {
    TestQuestionnaire.Assert.that("questionnaire-for-order.json", "OPA-Patient1")
        .withData("o2_peter_bundle.json")
        .withParameters(new Parameters()
            .addParameter(new Parameters.ParametersParameterComponent(new StringType("ClaimId"))
                .setValue(new StringType("OPA-Claim1"))))
        .populate().isEqualsTo("questionnaire-response-populated-noLibrary.json");
  }

  @Test
  void testPopulate_HasErrors() {
    TestQuestionnaire.Assert.that("questionnaire-for-order-errors.json", "OPA-Patient1")
        .withData("o2_peter_bundle.json").withLibrary("outpatientPA.json")
        .withParameters(new Parameters()
            .addParameter(new Parameters.ParametersParameterComponent(new StringType("ClaimId"))
                .setValue(new StringType("OPA-Claim1"))))
        .populate().isEqualsTo("questionnaire-response-populated-errors.json");
  }
}