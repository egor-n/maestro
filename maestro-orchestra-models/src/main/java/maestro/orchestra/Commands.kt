/*
 *
 *  Copyright (c) 2022 mobile.dev inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package maestro.orchestra

import maestro.KeyCode
import maestro.Point
import maestro.orchestra.util.Env.injectEnv

sealed interface Command {

    fun description(): String

    fun injectEnv(env: Map<String, String>): Command

}

data class SwipeCommand(
    val startPoint: Point,
    val endPoint: Point,
) : Command {

    override fun description(): String {
        return "Swipe from (${startPoint.x},${startPoint.y}) to (${endPoint.x},${endPoint.y})"
    }

    override fun injectEnv(env: Map<String, String>): SwipeCommand {
        return this
    }

}

class ScrollCommand : Command {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "ScrollCommand()"
    }

    override fun description(): String {
        return "Scroll vertically"
    }

    override fun injectEnv(env: Map<String, String>): ScrollCommand {
        return this
    }

}

class BackPressCommand : Command {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "BackPressCommand()"
    }

    override fun description(): String {
        return "Press back"
    }

    override fun injectEnv(env: Map<String, String>): BackPressCommand {
        return this
    }
}

class HideKeyboardCommand : Command {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "HideKeyboardCommand()"
    }

    override fun description(): String {
        return "Hide Keyboard"
    }

    override fun injectEnv(env: Map<String, String>): HideKeyboardCommand {
        return this
    }
}

data class TapOnElementCommand(
    val selector: ElementSelector,
    val retryIfNoChange: Boolean? = null,
    val waitUntilVisible: Boolean? = null,
    val longPress: Boolean? = null,
) : Command {

    override fun description(): String {
        return "Tap on ${selector.description()}"
    }

    override fun injectEnv(env: Map<String, String>): TapOnElementCommand {
        return copy(
            selector = selector.injectEnv(env),
        )
    }
}

data class TapOnPointCommand(
    val x: Int,
    val y: Int,
    val retryIfNoChange: Boolean? = null,
    val waitUntilVisible: Boolean? = null,
    val longPress: Boolean? = null,
) : Command {

    override fun description(): String {
        return "Tap on point ($x, $y)"
    }

    override fun injectEnv(env: Map<String, String>): TapOnPointCommand {
        return this
    }
}

data class AssertCommand(
    val visible: ElementSelector? = null,
    val notVisible: ElementSelector? = null,
    val timeout: Long? = null,
) : Command {

    override fun description(): String {
        val timeoutStr = timeout?.let { " within $timeout ms" } ?: ""
        if (visible != null) {
            return "Assert visible ${visible.description()}" + timeoutStr
        }

        if (notVisible != null) {
            return "Assert not visible ${notVisible.description()}" + timeoutStr
        }

        return "No op"
    }

    override fun injectEnv(env: Map<String, String>): AssertCommand {
        return copy(
            visible = visible?.injectEnv(env),
            notVisible = notVisible?.injectEnv(env),
        )
    }
}

data class InputTextCommand(
    val text: String
) : Command {

    override fun description(): String {
        return "Input text $text"
    }

    override fun injectEnv(env: Map<String, String>): InputTextCommand {
        return copy(
            text = text.injectEnv(env)
        )
    }
}

data class LaunchAppCommand(
    val appId: String,
    val clearState: Boolean? = null,
) : Command {

    override fun description(): String {
        return if (clearState != true) {
            "Launch app \"$appId\""
        } else {
            "Launch app \"$appId\" with clear state"
        }
    }

    override fun injectEnv(env: Map<String, String>): LaunchAppCommand {
        return this
    }
}

data class ApplyConfigurationCommand(
    val config: MaestroConfig,
) : Command {

    override fun description(): String {
        return "Apply configuration"
    }

    override fun injectEnv(env: Map<String, String>): ApplyConfigurationCommand {
        return copy(
            config = config.injectEnv(env),
        )
    }
}

data class OpenLinkCommand(
    val link: String
) : Command {

    override fun description(): String {
        return "Open $link"
    }

    override fun injectEnv(env: Map<String, String>): OpenLinkCommand {
        return copy(
            link = link.injectEnv(env),
        )
    }
}

data class PressKeyCommand(
    val code: KeyCode,
) : Command {
    override fun description(): String {
        return "Press ${code.description} key"
    }

    override fun injectEnv(env: Map<String, String>): PressKeyCommand {
        return this
    }

}

data class EraseTextCommand(
    val charactersToErase: Int,
) : Command {

    override fun description(): String {
        return "Erase $charactersToErase characters"
    }

    override fun injectEnv(env: Map<String, String>): EraseTextCommand {
        return this
    }

}

data class TakeScreenshotCommand(
    val path: String,
) : Command {

    override fun description(): String {
        return "Take screenshot $path"
    }

    override fun injectEnv(env: Map<String, String>): TakeScreenshotCommand {
        return copy(
            path = path.injectEnv(env),
        )
    }
}
