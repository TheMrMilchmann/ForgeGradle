/*
 * ForgeGradle
 * Copyright (C) 2018 Forge Development LLC
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package net.minecraftforge.gradle.userdev.tasks;

import org.gradle.api.tasks.compile.JavaCompile;

// A terrible hack to use JavaCompile while bypassing
// Gradle's normal task infrastructure.
public class HackyJavaCompile extends JavaCompile {

    public void doHackyCompile() {

        // What follows is a horrible hack to allow us to call JavaCompile
        // from our dependency resolver.
        // As described in https://github.com/MinecraftForge/ForgeGradle/issues/550,
        // invoking Gradle tasks in the normal way can lead to deadlocks
        // when done from a dependency resolver.

        // To avoid these issues, we invoke the 'compile' method on JavaCompile
        // using reflection.

        // Normally, the output history is set by Gradle. Since we're bypassing
        // the normal gradle task infrastructure, we need to do it ourselves.
        this.getOutputs().setPreviousOutputFiles(this.getProject().files());

        // Do the actual compilation,
        // bypassing a bunch of Gradle's other stuff (e.g. internal event listener mechanism)
        this.compile();
    }

}
