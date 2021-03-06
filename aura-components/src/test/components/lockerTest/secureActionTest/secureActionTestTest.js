({
    /**
     * Note that this test file operates in system mode (objects are not Lockerized) so the tests delegate logic and
     * verification to the controller and helper files, which operate in user mode.
     */

    // LockerService not supported on older IE
    browsers: ["-IE8", "-IE9", "-IE10", "-IE11"],

    setUp: function(cmp) {
        cmp.set("v.testUtils", $A.test);
    },

    testServerActionIsSecureAction: {
        test: function(cmp) {
            cmp.testServerActionIsSecureAction();
        }
    },

    testClientActionIsSecureAction: {
        test: function(cmp) {
            cmp.testClientActionIsSecureAction();
        }
    },

    testActionThatErrors: {
        test: function(cmp) {
            cmp.testActionThatErrors();
            $A.test.addWaitFor(true, function() {
                return !!cmp.get("v.testComplete");
            });
        }
    },

    testSetParams: {
        test: function(cmp) {
            cmp.testSetParams();
            $A.test.addWaitFor(true, function() {
                return !!cmp.get("v.testComplete");
            });
        }
    },

    testDifferentNamespacedActionPassedFromSystemMode: {
        test: function(cmp) {
            var facet = cmp.find("facet");
            var facetAction = facet.get("c.cExecuteInForegroundWithReturn");
            cmp.testDifferentNamespacedActionPassedFromSystemMode(facetAction);
        }
    },

    testGlobalControllerBlocked: {
        test: function(cmp) {
            cmp.testGlobalControllerBlocked();
        }
    },
})
