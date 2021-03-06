package duke.command;

import duke.task.TaskList;
import duke.exception.DukeException;
import duke.parser.DataParser;
import duke.parser.DateParser;
import duke.ui.Ui;
import duke.storage.Storage;

/**
 * Represents a Command to list all commands available.
 */
public class ListCommandsCommand extends Command {

    /**
     * Constructs a new Command where it does not terminate the Duke Application.
     */
    public ListCommandsCommand() {
        super(false);
    }

    /**
     * Executes the specific command based on the type of the command.
     * In this case, it asks the ui to list all the comamnds available.
     * @param taskList The List of tasks involved.
     * @param ui The Interface which deals with user input and interaction.
     * @param storage The storage to load and save task data into the output file.
     * @param dataParser Parses user and task data.
     * @param dateParser Parser date data.
     * @throws DukeException If there is a problem with data processing, loading or saving.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage,
                        DataParser dataParser, DateParser dateParser) throws DukeException {
        ui.listCommands();
    }

    /**
     * Returns a String representation for user guidance.
     * @return a String representation for user guidance.
     */
    public String toString() {
        String helper = "List all commands: \n"
            + "Usage: help";
        return helper;
    }
}
