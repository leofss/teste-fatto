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
    const [year, month, day] = dateString.split("-");
    return `${day}/${month}/${year}`; 
  };

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(amount);
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
        {formatCurrency(task.amount)}
      </td>
      <td
        className={`h-12 text-2xl max-[640px]:text-base ${backgroundColor} text-white text-center`}
      >
        {formatDate(task.dueDate)}
      </td>
      <td
        className={`h-12 text-2xl max-[640px]:text-base ${backgroundColor} text-white text-center`}
      >
        {task.displayOrder}
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
