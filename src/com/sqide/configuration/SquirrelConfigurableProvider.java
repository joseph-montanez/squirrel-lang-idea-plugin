package com.sqide.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.sqide.sdk.SquirrelSdkService;
import com.sqide.util.SquirrelConstants;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SquirrelConfigurableProvider extends ConfigurableProvider {
    @NotNull
    private final Project myProject;

    public SquirrelConfigurableProvider(@NotNull Project project) {
        myProject = project;
    }

    @Nullable
    @Override
    public Configurable createConfigurable() {
        Configurable sdkConfigurable = SquirrelSdkService.getInstance(myProject).createSdkConfigurable();
        if (sdkConfigurable != null) {
            return new SquirrelCompositeConfigurable(sdkConfigurable);
        } else {
            return new SquirrelCompositeConfigurable();
        }
    }

    private static class SquirrelCompositeConfigurable extends SearchableConfigurable.Parent.Abstract {
        private Configurable[] myConfigurables;

        public SquirrelCompositeConfigurable(Configurable... configurables) {
            myConfigurables = configurables;
        }

        @Override
        protected Configurable[] buildConfigurables() {
            return myConfigurables;
        }

        @NotNull
        @Override
        public String getId() {
            return "squirrel";
        }

        @Nls
        @Override
        public String getDisplayName() {
            return SquirrelConstants.SQUIRREL;
        }

        @Nullable
        @Override
        public String getHelpTopic() {
            return null;
        }

        @Override
        public void disposeUIResources() {
            super.disposeUIResources();
            myConfigurables = null;
        }
    }
}
