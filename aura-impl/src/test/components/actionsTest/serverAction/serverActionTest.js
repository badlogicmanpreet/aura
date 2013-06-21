({
    /**
     * Test the server side action is a background action
     */
    testServerActionIsBackground : {
        test : function(component) {
            var foregroundAction = component.get("c.executeInForeground");
            $A.test.assertFalse(foregroundAction.isBackground(), "foreground action should have had isBackground === false");
            var foregroundActionWR = component.get("c.executeInForegroundWithReturn");
            $A.test.assertFalse(foregroundActionWR.isBackground(), "foreground action with return should have had isBackground === false");
            var backgroundAction = component.get("c.executeInBackground");
            $A.test.assertTrue(backgroundAction.isBackground(), "background action should have had isBackground === true");
            var backgroundActionWR = component.get("c.executeInBackgroundWithReturn");
            $A.test.assertTrue(backgroundActionWR.isBackground(), "background action with return should have had isBackground === true");
            

            var wasFG = component.get("c.executeInForeground");
            wasFG.setBackground(true);
            $A.test.assertTrue(wasFG.isBackground(), "wasFG action should have had isBackground set to true");
            var stillBG = component.get("c.executeInBackground");
            stillBG.setBackground(false);
            $A.test.assertTrue(stillBG.isBackground(), "stillBG action should not have had isBackground set to false");
        }
    },
    testClientActionIsBackground : {
    	test : function(component) {
    		var clientSideAction = component.get("c.clientSideAction");
            $A.test.assertFalse(clientSideAction.isBackground(), "clientSideAction should have had isBackground === false");
            
            clientSideAction.setBackground(true);
            $A.test.assertTrue(clientSideAction.isBackground(), "clientSideAction should have had isBackground === true after calling setBackground(true)");
    		
            var clientSideAction = component.get("c.clientSideAction");
            $A.test.assertFalse(clientSideAction.isBackground(), "a freshly created clientSideAction should have had isBackground === false");
    	}
    }
})
