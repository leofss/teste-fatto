import React from "react";
import { Trash2, Pencil, MoveUp, MoveDown } from "lucide-react";

const Task = ({
  task,
  onEdit,
  onDelete,
  onMoveUp,
  onMoveDown,
  disableMoveUp,
  disableMoveDown,
}) => {
  const backgroundColor =
    task.amount > 1000 ? "bg-yellow-300" : "bg-indigo-700";

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  };

  return (
    <>
      <td
        className={`h-12 text-2xl  max-[640px]:text-base ${backgroundColor} text-white text-center`}
      >
        {task.title}
      </td>
      <td
        className={`h-12 text-2xl max-[640px]:text-base ${backgroundColor} text-white text-center`}
      >
        {task.amount}
      </td>
      <td
        className={`h-12 text-2xl max-[640px]:text-base ${backgroundColor} text-white text-center`}
      >
        {formatDate(task.dueDate)}
      </td>
      <td
        className={`flex items-center max-[640px]:h-20 max-[640px]:gap-0 text-2xl justify-center gap-4 h-12 ${backgroundColor} text-white text-center`}
      >
        <div>
          <button onClick={onDelete}>
            <Trash2 />
          </button>
          <button onClick={onEdit}>
            <Pencil />
          </button>
        </div>

        <div>
          <button onClick={onMoveUp} disabled={disableMoveUp}>
            <MoveUp />
          </button>
          <button onClick={onMoveDown} disabled={disableMoveDown}>
            <MoveDown />
          </button>
        </div>
      </td>
    </>
  );
};

export default Task;
