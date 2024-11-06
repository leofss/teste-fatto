import React, { useRef } from "react";
import { X } from "lucide-react";

function Modal({ onClose, children }) {
  const modalRef = useRef();

  const closeModal = (e) => {
    if (modalRef.current === e.target) {
      onClose();
    }
  };

  return (
    <div
      ref={modalRef}
      onClick={closeModal}
      className="fixed inset-0 bg-black bg-opacity-30 backdrop-blur-sm flex justify-center items-center"
    >
      <div className="mt-10 flex flex-col gap-5 text-white">
        <button className="place-self-end" onClick={onClose}>
          <X size={30} />
        </button>
        <div className="bg-indigo-700 rounded-xl px-20 py-10 flex flex-col gap-5 items-center mx-4">{children}</div>
      </div>
    </div>
  );
}

export default Modal;
